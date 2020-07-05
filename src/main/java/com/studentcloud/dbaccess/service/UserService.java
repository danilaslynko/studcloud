package com.studentcloud.dbaccess.service;

import com.studentcloud.dbaccess.auth.Role;
import com.studentcloud.dbaccess.auth.User;
import com.studentcloud.dbaccess.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final MailService mailService;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${hostname}")
    String hostname;

    public UserService(UserRepo userRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.mailService = mailService;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void activateUser(String activationCode) {
        Optional<User> user = userRepo.findByActivationCode(activationCode);
        if (user.isPresent()) {
            user.get().setActive(true);
            user.get().setActivationCode(null);
            userRepo.save(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "В системе нет такого пользователя");
        }
    }

    public String addUser(User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Utils.getErrors(bindingResult, model);
            return "registration";

        } else {
            User userFromDb = userRepo.findByUsername(user.getUsername());

            String regexp = "((?=.*\\d)(?=.*[a-zA-Z]).{8,20})";

            if (userFromDb != null) {
                model.addAttribute(
                        "usernameExistsError",
                        "Пользователь с таким именем уже существует"
                );
                return "registration";
            }
            if (!user.getPassword().matches(regexp)) {
                model.addAttribute(
                        "passwordDoesntMatchRegexError",
                        "Пароль должен содержать цифры и буквы латинского алфавита и состоять из 8-20 символов"
                );
                return "registration";
            }
            if (!user.getPassword().equals(user.getPasswordValidation())) {
                model.addAttribute(
                        "passwordsDoesntEqualsError",
                        "Пароли не совпадают"
                );
                return "registration";
            }

            user.setActive(false);
            user.setRoles(Collections.singleton(Role.ROLE_USER));

            user.setPassword(encoder.encode(user.getPassword()));

            sendActivationCode(user);

            userRepo.save(user);
        }

        return "redirect:/login?registered";
    }

    public void sendActivationCode(User user) {
        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);

        String message = String.format("Здравствуйте, %s!\r\n" +
                        "Для подтверждения почты пройдите по ссылке:\r\n%s" +
                        "user/activate?activationCode=%s",
                user.getUsername(),
                hostname,
                activationCode);

        mailService.send(user.getEmail(), "Активация аккаунта", message);
    }

    public void updateProfile(User user, String password, String email) {
        boolean isEmailUpdated = !(StringUtils.isEmpty(email) && email.equals(user.getEmail()));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (isEmailUpdated) {
            SecurityContextHolder.getContext().setAuthentication(null);
            user.setEmail(email);
            user.setActive(false);

            sendActivationCode(user);

            if (StringUtils.isEmpty(password) && !user.getPassword().equals(password)) {
                user.setPassword(password);
            }
        }

        userRepo.save(user);
    }
}

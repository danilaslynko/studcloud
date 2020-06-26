package com.studentcloud.dbaccess.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtils {

    public static void getErrors(BindingResult bindingResult, Model model) {
        Map<String, String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        ));

        model.mergeAttributes(errors);
    }
}

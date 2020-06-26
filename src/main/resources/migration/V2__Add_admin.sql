insert into studcloud_db.user (id, username, password, active)
values (1, '1zrs18017', '$2a$08$p.H8S0EY5evU3Uah43rWeuOVhrkwik46NFK2UwVsP7K/idDlFkIAC', true);

insert into user_role (user_id, roles)
    values (1, 'ROLE_USER'), (1, 'ROLE_ADMIN');
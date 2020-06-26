create table comment
(
    id bigint not null auto_increment,
    date date not null,
    message varchar(2048) not null,
    user_name varchar(255) not null,
    file_id bigint not null,
    primary key (id)
)
    engine=InnoDB;

create table department
(
    id bigint not null auto_increment,
    department_name varchar(255) not null,
    university_id bigint not null,
    primary key (id)
)
    engine=InnoDB;

create table department_subjects
(
    department_id bigint not null,
    subject_id bigint not null,
    primary key (department_id, subject_id)
)
    engine=InnoDB;

create table department_teachers
(
    department_id bigint not null,
    teacher_id bigint not null,
    primary key (department_id, teacher_id)
)
    engine=InnoDB;

create table file
(
    id bigint not null auto_increment,
    date date,
    file_name varchar(255) not null,
    department_id bigint not null,
    subj_id bigint not null,
    teacher_id bigint not null,
    university_id bigint not null,
    user_id bigint not null, primary key (id)
)
    engine=InnoDB;

create table subject
(
    id bigint not null auto_increment,
    subject_name varchar(255) not null,
    primary key (id)
)
    engine=InnoDB;

create table teacher
(
    id bigint not null auto_increment,
    teacher_name varchar(255) not null,
    primary key (id)
)
    engine=InnoDB;

create table teacher_subjects
(
    teacher_id bigint not null,
    subject_id bigint not null,
    primary key (teacher_id, subject_id)
)
    engine=InnoDB;

create table university
(
    id bigint not null auto_increment,
    university_name varchar(255) not null,
    primary key (id)
)
    engine=InnoDB;

create table university_subjects
(
    university_id bigint not null,
    subject_id bigint not null,
    primary key (university_id, subject_id)
)
    engine=InnoDB;

create table university_teachers
(
    university_id bigint not null,
    teacher_id bigint not null,
    primary key (university_id, teacher_id)
)
    engine=InnoDB;

create table usr
(
    id bigint not null auto_increment,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
)
    engine=InnoDB;

create table user_role
(
    user_id bigint not null,
    roles varchar(255)
)
    engine=InnoDB;

alter table department
    add constraint department_department_name unique (department_name);

alter table subject
    add constraint subject_subject_name unique (subject_name);

alter table teacher
    add constraint teacher_teacher_name unique (teacher_name);

alter table university
    add constraint university_university_name unique (university_name);

alter table comment
    add constraint comment_file_id foreign key (file_id) references file (id);

alter table department
    add constraint department_university_id foreign key (university_id) references university (id);

alter table department_subjects
    add constraint department_subjects_subject_id foreign key (subject_id) references subject (id);

alter table department_subjects
    add constraint department_subjects_department_id foreign key (department_id) references department (id);

alter table department_teachers
    add constraint department_teachers_teacher_id foreign key (teacher_id) references teacher (id);

alter table department_teachers
    add constraint department_teachers_department_id foreign key (department_id) references department (id);

alter table file
    add constraint file_department_id foreign key (department_id) references department (id);

alter table file
    add constraint file_subj_id foreign key (subj_id) references subject (id);

alter table file
    add constraint file_teacher_id foreign key (teacher_id) references teacher (id);

alter table file
    add constraint file_university_id foreign key (university_id) references university (id);

alter table file
    add constraint file_user_id foreign key (user_id) references usr (id);

alter table teacher_subjects
    add constraint teacher_subjects_subject_id foreign key (subject_id) references subject (id);

alter table teacher_subjects
    add constraint teacher_subjects_teacher_id foreign key (teacher_id) references teacher (id);

alter table university_subjects
    add constraint university_subjects_subject_id foreign key (subject_id) references subject (id);

alter table university_subjects
    add constraint university_subjects_university_id foreign key (university_id) references university (id);

alter table university_teachers
    add constraint university_teachers_teacher_id foreign key (teacher_id) references teacher (id);

alter table university_teachers
    add constraint university_teachers_university_id foreign key (university_id) references university (id);

alter table user_role
    add constraint user_role_user_id foreign key (user_id) references usr (id);

insert into usr (id, username, password, active)
values (1, '1zrs18017', '$2a$08$p.H8S0EY5evU3Uah43rWeuOVhrkwik46NFK2UwVsP7K/idDlFkIAC', true);

insert into user_role (user_id, roles)
values (1, 'ROLE_USER'), (1, 'ROLE_ADMIN');

create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
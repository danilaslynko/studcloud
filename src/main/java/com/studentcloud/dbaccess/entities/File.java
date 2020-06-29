package com.studentcloud.dbaccess.entities;

import com.studentcloud.dbaccess.auth.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String name;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "university_id", referencedColumnName="id", nullable = false)
    private University university;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "teacher_id", referencedColumnName="id",nullable = false)
    private Teacher teacher;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "department_id",referencedColumnName="id",nullable = false)
    private Department department;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "subj_id", referencedColumnName="id",nullable = false)
    private Subject subj;

    private Date date;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    public List<Comment> comments;

    public File(
            String name,
            University university,
            Teacher teacher,
            Department department,
            Subject subj,
            Date date,
            User user
    ) {
        this.name = name;
        this.university = university;
        this.teacher = teacher;
        teacher.getFiles().add(this);
        this.department = department;
        this.subj = subj;
        this.date = date;
        this.comments = new ArrayList<>();
        this.user = user;
        user.getFiles().add(this);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public File() {
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Subject getSubj() {
        return subj;
    }

    public void setSubj(Subject subj) {
        this.subj = subj;
    }
}

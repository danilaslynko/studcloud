package com.studentcloud.dbaccess.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "university_name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private Set<Department> departments;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.REFRESH }
    )
    @JoinTable(
            name = "university_teachers",
            joinColumns = @JoinColumn(name = "university_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teachers;

    @ManyToMany(
            cascade = { CascadeType.PERSIST, CascadeType.REFRESH }
    )
    @JoinTable(
            name = "university_subjects",
            joinColumns = @JoinColumn(name = "university_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;

    public University(String name) {
        this.name = name;
        this.departments = new HashSet<>();
        this.subjects = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public University() {
        this.departments = new HashSet<>();
        this.subjects = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof University)) return false;
        University that = (University) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName());
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}

package com.studentcloud.dbaccess.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name", unique = true, nullable = false)
    private String name;

    @ManyToMany(
            mappedBy = "subjects",
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    private Set<Department> departments;

    @ManyToMany(
            mappedBy = "subjects",
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    private Set<University> universities;

    @ManyToMany(
            mappedBy = "subjects",
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    private Set<Teacher> teachers;

    public Subject() {
    }

    public Subject(String name) {
        this.departments = new HashSet<>();
        this.teachers = new HashSet<>();
        this.universities = new HashSet<>();
        this.name = name;
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

    public Set<University> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<University> universities) {
        this.universities = universities;
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
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return Objects.equals(getName(), subject.getName());
    }
}

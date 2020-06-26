package com.studentcloud.dbaccess.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToMany(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinTable(
            name = "department_subjects",
            joinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
    )
    private Set<Subject> subjects;

    @Column(name = "department_name", unique = true, nullable = false)
    private String name;

    @ManyToOne(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "university_id", referencedColumnName = "id", nullable = false)
    private University university;

    @ManyToMany(
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinTable(
            name = "department_teachers",
            joinColumns = @JoinColumn(name = "department_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName="id")
    )
    private Set<Teacher> teachers;

    public Department(String name, University university) {
        this.name = name;
        this.university = university;
        this.teachers = new HashSet<>();
        this.subjects = new HashSet<>();
    }

    public Department() {
        this.teachers = new HashSet<>();
        this.subjects = new HashSet<>();
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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
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
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getName().equals(that.getName());
    }
}

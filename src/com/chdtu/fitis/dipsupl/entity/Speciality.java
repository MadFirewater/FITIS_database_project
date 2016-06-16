package com.chdtu.fitis.dipsupl.entity;

import javax.persistence.*;
import java.util.Set;



@Entity
@Table(name="SPECIALITIES")
public class Speciality {
    @Id
    @Column(name= "ID")
    private int id;

    @Column(name="NAME")
    private String name;

    //FAKULTET ID
    //KAFEDRA ID=PIDROZDILI ID
    @Column(name="NAME_BACH")
    private String bachelorName;

    @ManyToOne
    @JoinColumn(name="KAFEDRA_ID")
    private Cathedra cathedra;


    @OneToMany(mappedBy = "speciality")
    private Set<Subject> subjects;

    @ManyToOne
    @JoinColumn (name = "FAKULTET_ID")
    private Department department;

    @Column(name = "FAKULTET_ID",insertable = false, updatable = false)
    private int departmentId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBachelorName() {
        return bachelorName;
    }

    public void setBachelorName(String bachelorName) {
        this.bachelorName = bachelorName;
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}

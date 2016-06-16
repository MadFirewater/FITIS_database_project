package com.chdtu.fitis.dipsupl.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;


@Entity
@Table(name = "SUBJECTS")
public class Subject {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "SPECIALITY_ID")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "KC_ID")
    private KnowledgeControl knowledgeControl;

    @Column(name = "KC_ID",insertable = false, updatable = false)
    private int knowledgeControlID;

    @OneToMany(mappedBy="subject",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GroupSubject> groupSubjects;

    @Column(name = "SEMESTER")
    private int semester;



    @Column(name = "GODIN")
    private int hours;

    @Column(name = "ACTIVE1")
    private boolean active;


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


    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public KnowledgeControl getKnowledgeControl() {
        return knowledgeControl;
    }

    public void setKnowledgeControl(KnowledgeControl knowledgeControl) {
        this.knowledgeControl = knowledgeControl;
    }

    public void setGroupSubjects(Set<GroupSubject> groupSubjects) {
        this.groupSubjects = groupSubjects;
    }

    public Set<GroupSubject> getGroupSubjects() {
        return groupSubjects;
    }

    public int getKnowledgeControlID() {
        return knowledgeControlID;
    }

    public void setKnowledgeControlID(int knowledgeControlID) {
        this.knowledgeControlID = knowledgeControlID;
    }
}

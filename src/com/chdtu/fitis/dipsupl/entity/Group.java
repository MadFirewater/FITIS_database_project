package com.chdtu.fitis.dipsupl.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "GROUPS")
public class Group {

    @Id
    @Column (name = "ID")
    private int id;

    @Column (name = "NAME")
    private String name;
    @ManyToOne
    @JoinColumn (name = "SPECIALITY_ID")
    private Speciality speciality;

    @Column (name="SPECIALITY_ID",insertable = false, updatable = false)
    private int specialityId;

    @Column (name = "TUTION_FORM")
    private char modeOfStudy;

    @Column (name = "CREATION_YEAR")
    private int creationYear;

    @Column (name = "KURS")
    private int studyStartYear;

    @Column (name = "ACTIVE1")
    @Type(type="true_false")
    private boolean active;

    @OneToMany(mappedBy ="group")
    private Set<Student> students;

    @OneToMany(mappedBy="group",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GroupSubject> groupSubjects;

    @ManyToMany
    @JoinTable(name = "SUBJECTS_FOR_GROUPS", joinColumns = {
            @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) },
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID"))
    private Set<Subject> subjects = new HashSet<Subject>(0);

    public static final int SEMESTERS_FOR_FULL_TIME_GROUP = 8;
    public static final int SEMESTERS_FOR_EXTRAMURAL_GROUP = 10;

    public int calculateLastSemester() {
        int lastSemester = 0;
        if (modeOfStudy == 'ä') {
            lastSemester = SEMESTERS_FOR_FULL_TIME_GROUP;
        }
        if (modeOfStudy == 'ç') {
            lastSemester = SEMESTERS_FOR_EXTRAMURAL_GROUP;
        }
        return lastSemester;
    }

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

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public char getModeOfStudy() {
        return modeOfStudy;
    }

    public void setModeOfStudy(char tutionForm) {
        this.modeOfStudy = tutionForm;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    public int getStudyStartYear() {
        return studyStartYear;
    }

    public void setStudyStartYear(int studyStartYear) {
        this.studyStartYear = studyStartYear;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<GroupSubject> getGroupSubjects() {
        return groupSubjects;
    }

    public void setGroupSubjects(Set<GroupSubject> groupSubjects) {
        this.groupSubjects = groupSubjects;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }
}

package com.chdtu.fitis.dipsupl.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@IdClass(GradePrimaryKey.class)
@Table(name = "GRADES")
public class Grade implements Serializable{

    @Id
    @Column(name = "STUD_ID",insertable = false, updatable = false)
    private int studentId;

    @Id
    @Column(name = "SUBJ_ID",insertable = false, updatable = false)
    private int subjectId;

    @Column(name = "GRADE")
    private Integer grade;

    @Column(name = "POINTS", nullable = true)
    private Integer points;

    @Column(name = "ECTS", nullable = true)
    private String gradeECTS;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "STUD_ID")
    private Student student;

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "SUBJ_ID")
    private Subject subject;


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getGradeECTS() {
        return gradeECTS;
    }

    public void setGradeECTS(String gradeECTS) {
        this.gradeECTS = gradeECTS;
    }
}
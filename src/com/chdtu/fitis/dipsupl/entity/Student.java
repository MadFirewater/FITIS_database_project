package com.chdtu.fitis.dipsupl.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;



@Entity
@Table(name = "STUDENTS")
public class Student {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PATRONIMIC")
    private String patronimic;

    @Column (name = "ACTIVE1")
    @Type(type="true_false")
    private boolean inActive;

    @ManyToOne(targetEntity = Group.class)
    @JoinColumn(name = "GROUP_ID")
    private Group group;
    @Embedded


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronimic() {
        return patronimic;
    }

    public void setPatronimic(String patronimic) {
        this.patronimic = patronimic;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isInActive() {
        return inActive;
    }

    public void setInActive(boolean inActive) {
        this.inActive = inActive;
    }

    public String getStudentFullName(){
        String result=surname+" "+name+" "+patronimic;
        return result;
    }

    public String getInitials(){
        String result=surname+" "+name.substring(0,1)+"."+patronimic.substring(0,1)+".";
        return result;
    }


}


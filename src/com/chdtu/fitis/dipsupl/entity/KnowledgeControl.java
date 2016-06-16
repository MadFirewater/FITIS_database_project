package com.chdtu.fitis.dipsupl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="KNOWLEDGE_CONTROL")
public class KnowledgeControl {
    @Id
    @Column(name = "ID" )
    private int id;

    @Column(name = "NAME" )
    private String name;

    @Column(name = "GRADE" )
    private boolean grade;

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

    public boolean getGrade() {
        return grade;
    }

    public void setGrade(boolean grade) {
        this.grade = grade;
    }
}

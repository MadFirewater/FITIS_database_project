package com.chdtu.fitis.dipsupl.entity;

import java.io.Serializable;


public class GradePrimaryKey implements Serializable {
    protected int studentId;
    protected int subjectId;

    public GradePrimaryKey(){

    }

    public GradePrimaryKey(int student, int subject){
        this.studentId =student;
        this.subjectId =subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradePrimaryKey that = (GradePrimaryKey) o;

        if (studentId != that.studentId) return false;
        return subjectId == that.subjectId;

    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + subjectId;
        return result;
    }
}

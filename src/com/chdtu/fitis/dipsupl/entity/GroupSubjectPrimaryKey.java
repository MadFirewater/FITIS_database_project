package com.chdtu.fitis.dipsupl.entity;

import java.io.Serializable;


public class GroupSubjectPrimaryKey implements Serializable {
    protected int subjectId;
    protected int groupId;



    public GroupSubjectPrimaryKey(){

    }

    public GroupSubjectPrimaryKey(int subjectId, int groupId) {
        this.subjectId = subjectId;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupSubjectPrimaryKey that = (GroupSubjectPrimaryKey) o;

        if (subjectId != that.subjectId) return false;
        return groupId == that.groupId;

    }

    @Override
    public int hashCode() {
        int result = subjectId;
        result = 31 * result + groupId;
        return result;
    }
}

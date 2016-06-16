package com.chdtu.fitis.dipsupl.entity;

import com.chdtu.fitis.dipsupl.entity.Group;
import com.chdtu.fitis.dipsupl.entity.Subject;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@IdClass(GroupSubjectPrimaryKey.class)
@Table (name = "SUBJECTS_FOR_GROUPS")
public class GroupSubject implements Serializable {
    @Id
    @Column(name = "Subject_ID",insertable = false, updatable = false)
    private int subjectId;


    @Id
    @Column(name = "GROUP_ID",insertable = false, updatable = false)
    private int groupId;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @ManyToOne
    @JoinColumn (name = "GROUP_ID")
    private Group group;

    @Column (name = "IN_DODATOK")
    @Type(type="true_false")
    private boolean inDiplomaAddition;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isInDiplomaAddition() {
        return inDiplomaAddition;
    }

    public void setInDiplomaAddition(boolean inDiplomaAddition) {
        this.inDiplomaAddition = inDiplomaAddition;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

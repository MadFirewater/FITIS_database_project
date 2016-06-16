package com.chdtu.fitis.dipsupl.grademodel;

import com.chdtu.fitis.dipsupl.entity.Grade;
import com.chdtu.fitis.dipsupl.entity.Student;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Алекс on 28.04.2016.
 */
public class GroupSummary {
    ArrayList<StudentSummary> studentGradesSummaries;
    String groupName;

    public GroupSummary(ArrayList<Student> students, ArrayList<ArrayList<ArrayList<Grade>>> gradesForGroup) {
        studentGradesSummaries = new ArrayList<>();
        for (int i = 0; i < gradesForGroup.size(); i++) {
            studentGradesSummaries.add(new StudentSummary(students.get(i), gradesForGroup.get(i)));
        }
        this.groupName = students.get(0).getGroup().getName();

        studentGradesSummaries.sort(new Comparator<StudentSummary>() {
            @Override
            public int compare(StudentSummary o1, StudentSummary o2) {
                Collator ukrainianCollator = Collator.getInstance(new Locale("uk", "UA"));
                return ukrainianCollator.compare(o1.getStudentName(), o2.getStudentName());
            }
        });
    }

    public ArrayList<StudentSummary> getStudentGradesSummaries() {
        return studentGradesSummaries;
    }

    public void setStudentGradesSummaries(ArrayList<StudentSummary> studentGradesSummaries) {
        this.studentGradesSummaries = studentGradesSummaries;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}

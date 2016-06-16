package com.chdtu.fitis.dipsupl.docmanager;

import com.chdtu.fitis.dipsupl.entity.Grade;
import com.chdtu.fitis.dipsupl.entity.Group;
import com.chdtu.fitis.dipsupl.entity.Student;
import com.chdtu.fitis.dipsupl.grademodel.GroupSummary;
import com.chdtu.fitis.dipsupl.grademodel.StudentSummary;
import com.chdtu.fitis.dipsupl.servlet.SelectedItems;

import java.util.ArrayList;

import static com.chdtu.fitis.dipsupl.Query.*;

/**
 * Created by Supreme on 13.06.2016.
 */
public class DocumentBuilder {
    public static void createDocumentForStudentById(int studentId) {
        ArrayList<ArrayList<Grade>> grades;
        grades = getGrades(studentId);
        Student student = getStudent(studentId);
        StudentSummary studentSummary = new StudentSummary(student, grades);
        TableBuilder.buildDocument(studentSummary, generateStudentPathById(studentId));
    }

    public static void createDocumentForGroupById(int groupId) {
        ArrayList<ArrayList<ArrayList<Grade>>> gradeLists = new ArrayList();
        ArrayList<Student> students = (ArrayList) getSelectedStudents(groupId);
        for (Student student : students) {
            ArrayList<ArrayList<Grade>> grades = getGrades(student.getId());
            gradeLists.add(grades);
        }
        GroupSummary groupSummary = new GroupSummary(students, gradeLists);
        TableBuilder.buildDocument(groupSummary, generateGroupPathById(groupId));
    }


    public static String generateStudentPathById(int studentId) {
        Student student = getStudent(studentId);

        String result = SelectedItems.getFilePathPrefix()
                + student.getSurname()
                + student.getName().substring(0, 1)
                + "."
                + student.getPatronimic().substring(0, 1)
                + ". "
                + student.getGroup().getName()
                + ".docx";
        return result;
    }

    public static String generateGroupPathById(int groupId) {
        Group group = getGroup(groupId);
        String result = SelectedItems.getFilePathPrefix() + "Група "
                + group.getName()
                + ".docx";
        return result;
    }
}

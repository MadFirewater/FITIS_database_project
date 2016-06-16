package com.chdtu.fitis.dipsupl.servlet;

import com.chdtu.fitis.dipsupl.entity.Department;
import com.chdtu.fitis.dipsupl.entity.Group;
import com.chdtu.fitis.dipsupl.entity.Student;

import java.util.List;

/**
 * Created by Supreme on 24.04.2016.
 */

public class SelectedItems {

    private static String filePathPrefix = System.getProperty("user.home") + "\\Desktop\\";
    private static List<Department> departments;
    private static List<Group> groups;
    private static List<Student> students;
    private static int departmentId;
    private static int groupId;
    private static int studentId;
    private static String generatedFilePath;

    public static String getGeneratedFilePath() {
        return generatedFilePath;
    }

    public static void setGeneratedFilePath(String generatedFilePath) {
        SelectedItems.generatedFilePath = generatedFilePath;
    }

    public static String getFilePathPrefix() {
        return filePathPrefix;
    }

    public static void setFilePathPrefix(String filePathPrefix) {
        SelectedItems.filePathPrefix = filePathPrefix;
    }

    public static List<Department> getDepartments() {
        return departments;
    }

    public static void setDepartments(List<Department> departments) {
        SelectedItems.departments = departments;
    }

    public static List<Group> getGroups() {
        return groups;
    }

    public static void setGroups(List<Group> groups) {
        SelectedItems.groups = groups;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static void setStudents(List<Student> students) {
        SelectedItems.students = students;
    }

    public static int getDepartmentId() {
        return departmentId;
    }

    public static void setDepartmentId(int departmentId) {
        SelectedItems.departmentId = departmentId;
    }

    public static int getGroupId() {
        return groupId;
    }

    public static void setGroupId(int groupId) {
        SelectedItems.groupId = groupId;
    }

    public static int getStudentId() {
        return studentId;
    }

    public static void setStudentId(int studentId) {
        SelectedItems.studentId = studentId;
    }
}

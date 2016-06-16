package com.chdtu.fitis.dipsupl.grademodel;

import com.chdtu.fitis.dipsupl.entity.Grade;
import com.chdtu.fitis.dipsupl.entity.Student;

import java.util.ArrayList;

public class StudentSummary {
    public static final String[] TITLES = {"", "Курсові роботи (проекти)", "Практики", "Атестація"};
    private ArrayList<GradeSummary> gradeSummaries;
    private String studentName;
    private String groupName;
    private double averagePoint;
    private double averageScale;
    private int totalHours;
    private String totalCreditsECTS;
    private Student student;

    public StudentSummary(Student student, ArrayList<ArrayList<Grade>> gradeLists) {
        gradeSummaries = new ArrayList<>();
        for (int i = 0; i < gradeLists.size(); i++) {
            gradeSummaries.add(new GradeSummary(TITLES[i], student, gradeLists.get(i)));
        }
        studentName = student.getStudentFullName();
        groupName = student.getGroup().getName();
        averagePoint = calculateAveragePoint();
        averageScale = calculateAverageScale();
        totalHours = calculateTotalHours();
        totalCreditsECTS = calculateTotalCredits();
        this.student = student;
    }

    public double calculateAveragePoint() {
        double result = 0;
        int amount = 0;
        int sum = 0;
        for (GradeSummary gradeSummary : gradeSummaries) {
            for (CourseGrade courseGrade : gradeSummary.getCourseGrades()) {
                if (courseGrade.isMarkType() && courseGrade.getPoints() != 0) {
                    sum += courseGrade.getPoints();
                    amount++;
                }
            }
        }
        result = sum / (double) amount;
        return result;
    }

    public int calculateTotalHours() {
        int result = 0;
        for (GradeSummary gradeSummary : gradeSummaries) {
            for (CourseGrade courseGrade : gradeSummary.getCourseGrades()) {
                result += courseGrade.getHours();
            }
        }
        return result;
    }

    public String calculateTotalCredits() {
        double result = totalHours / 36.0;
        return CourseGrade.getValueInString(result);
    }

    public double calculateAverageScale() {
        double result = 0;
        int amount = 0;
        int sum = 0;
        for (GradeSummary gradeSummary : gradeSummaries) {
            for (CourseGrade courseGrade : gradeSummary.getCourseGrades()) {
                if (courseGrade.isMarkType()
                        && courseGrade.getGradeScale() != null
                        && courseGrade.getGradeScale() != 0
                        && courseGrade.getGradeScale() != 1
                        && courseGrade.getGradeScale() != 2
                        ) {
                    sum += courseGrade.getGradeScale();
                    amount++;
                }
            }
        }
        result = sum / (double) amount;
        return result;
    }

    public static String[] getTITLES() {
        return TITLES;
    }

    public ArrayList<GradeSummary> getGradeSummaries() {
        return gradeSummaries;
    }

    public void setGradeSummaries(ArrayList<GradeSummary> gradeSummaries) {
        this.gradeSummaries = gradeSummaries;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public double getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(double averagePoint) {
        this.averagePoint = averagePoint;
    }

    public double getAverageScale() {
        return averageScale;
    }

    public void setAverageScale(double averageScale) {
        this.averageScale = averageScale;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public String getTotalCreditsECTS() {
        return totalCreditsECTS;
    }

    public void setTotalCreditsECTS(String totalCreditsECTS) {
        this.totalCreditsECTS = totalCreditsECTS;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

package com.chdtu.fitis.dipsupl.grademodel;

import com.chdtu.fitis.dipsupl.entity.Grade;
import com.chdtu.fitis.dipsupl.entity.Student;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class GradeSummary {

    private String title;

    private String studentName;

    private String groupName;

    private String specialityName;

    private int practicesCount;

    private int practicesCreditsECTS;

    private double totalCreditsECTS;

    private int totalHours;

    private int totalPoints;

    private ArrayList<CourseGrade> courseGrades;

    public GradeSummary(String title, Student student, ArrayList<Grade> grades) {
        if (grades.size() == 0) {
            this.title = title;
            studentName = student.getSurname() + " " + student.getName() + " " + student.getPatronimic();
            groupName = student.getGroup().getName();
            courseGrades = new ArrayList<>();
            courseGrades.add(new CourseGrade(student));
        } else {
            this.title = title;
            studentName = student.getSurname() + " " + student.getName() + " " + student.getPatronimic();
            groupName = student.getGroup().getName();
            courseGrades = new ArrayList<>();

            for (Grade grade : grades) {
                courseGrades.add(new CourseGrade(grade, student));
            }

            List<CourseGrade> subjectsWithSameName;
            for (int i = 0; i < courseGrades.size(); i++) {
                subjectsWithSameName = new ArrayList<>();
                subjectsWithSameName.add(courseGrades.get(i));
                for (int j = i + 1; j < courseGrades.size(); j++) {
                    if (courseGrades.get(i).getSubjectName().trim().equals(courseGrades.get(j).getSubjectName().trim())) {
                        subjectsWithSameName.add(courseGrades.get(j));
                        courseGrades.remove(j);
                        j--;
                    }
                }
                courseGrades.set(i, combineCourseGrades(subjectsWithSameName));
            }
            setPeriodsToNormalView();
            totalCreditsECTS = calculateTotalCreditsECTS();
            totalHours = calculateTotalHours();
        }
        courseGrades.sort(new Comparator<CourseGrade>() {
            @Override
            public int compare(CourseGrade o1, CourseGrade o2) {
                Collator ukrainianCollator = Collator.getInstance(new Locale("uk", "UA"));
                return ukrainianCollator.compare(o1.getSubjectName(), o2.getSubjectName());
            }
        });
    }

    public CourseGrade combineCourseGrades(List<CourseGrade> courseGrades) {
        int[] years = new int[courseGrades.size() * 2];
        int i = 0;
        for (CourseGrade courseGrade : courseGrades) {
            years[i] = Integer.parseInt(courseGrade.getStudyingPeriod().substring(0, 4));
            years[i + 1] = Integer.parseInt(courseGrade.getStudyingPeriod().substring(5));
            i += 2;
        }
        int min = years[0];
        int max = years[0];
        for (i = 0; i < courseGrades.size() * 2; i++) {
            if (years[i] > max) max = years[i];
            if (years[i] < min) min = years[i];
        }
        String period = new String(min + "-" + max);
        CourseGrade resultGrade = courseGrades.get(0);
        int hours = 0;
        int creditsECTS = 0;
        double averagePoints;
        int sumPoints = 0;
        int sumCountedPoints = 0;
        double averageGradeScale;
        int sumGradeScale = 0;
        int sumCountedGradeScale = 0;
        int numberOfExams = 0;
        int numberOfCounted = 0;
        for (CourseGrade courseGrade : courseGrades) {
            hours += courseGrade.getHours();
            if (courseGrade.isMarkType()) {
                sumPoints += courseGrade.getPoints();
                if (courseGrade.getGradeScale() != null) sumGradeScale += courseGrade.getGradeScale();
                numberOfExams++;
            } else {
                sumCountedPoints += courseGrade.getPoints();
                if (courseGrade.getGradeScale() != null) sumCountedGradeScale += courseGrade.getGradeScale();
                numberOfCounted++;
            }
        }
        if (numberOfExams != 0) {
            averagePoints = sumPoints / (double) numberOfExams;
            averageGradeScale = sumGradeScale / (double) numberOfExams;
            resultGrade.setMarkType(true);
            double rightPoints[] = calculateRightScaleAndPoints(averageGradeScale, averagePoints);
            resultGrade.setGradeScale((int) rightPoints[0]);
            resultGrade.setPoints(rightPoints[1]);
        } else {
            averagePoints = sumCountedPoints / (double) numberOfCounted;
            averageGradeScale = sumCountedGradeScale / (double) numberOfCounted;
            resultGrade.setGradeScale((int)Math.round(averageGradeScale));
            resultGrade.setPoints((int)Math.round(averagePoints));
            resultGrade.setMarkType(false);
        }
        resultGrade.setHours(hours);
        resultGrade.setCreditsECTS(resultGrade.getCreditsFromHours(hours));
        resultGrade.setGradeECTS(resultGrade.getGradeECTSFromPoints());
        resultGrade.setGradeNationalScale(resultGrade.getNationalGradeScale());
        resultGrade.setStudyingPeriod(period);
        if (numberOfExams > 1) resultGrade.setMultipleSemester(true);
        if (numberOfExams==0&&numberOfCounted>1)resultGrade.setMultipleSemester(true);
        return resultGrade;
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

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public int getPracticesCount() {
        return practicesCount;
    }

    public void setPracticesCount(int practicesCount) {
        this.practicesCount = practicesCount;
    }

    public int getPracticesCreditsECTS() {
        return practicesCreditsECTS;
    }

    public void setPracticesCreditsECTS(int practicesCreditsECTS) {
        this.practicesCreditsECTS = practicesCreditsECTS;
    }

    public double getTotalCreditsECTS() {
        return totalCreditsECTS;
    }

    public void setTotalCreditsECTS(double totalCreditsECTS) {
        this.totalCreditsECTS = totalCreditsECTS;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public ArrayList<CourseGrade> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(ArrayList<CourseGrade> courseGrades) {
        this.courseGrades = courseGrades;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int calculateTotalHours() {
        int totalHours = 0;
        for (CourseGrade grade :
                courseGrades) {
            totalHours += grade.getHours();
        }
        return totalHours;
    }

    public int calculateTotalPoints() {
        int totalPoints = 0;
        for (CourseGrade grade : courseGrades) {
            totalPoints += grade.getPoints();
        }
        return totalPoints;
    }

    public int calculateAvailableGradesCount() {
        int result = 0;
        for (CourseGrade grade : courseGrades) {
            if (grade.getPoints() != 0) result++;
        }
        return result;
    }

    public double calculateTotalCreditsECTS() {
        double totalCredits = 0;
        for (CourseGrade grade :
                courseGrades) {
            totalCredits += grade.getCreditsECTS();
        }
        return totalCredits;
    }


    public void setPeriodsToNormalView() {
        for (CourseGrade grade : courseGrades) {
            if (grade.getStudyingPeriod() != null)
                if (grade.getStudyingPeriod().substring(0, 4).equals(grade.getStudyingPeriod().substring(5))) {
                    int year = Integer.parseInt(grade.getStudyingPeriod().substring(0, 4)) - 1;
                    grade.setStudyingPeriod(year + "-" + grade.getStudyingPeriod().substring(5));
                }
        }

    }

    public double[] calculateRightScaleAndPoints(double averageGradeScale, double averagePoints) {
        double[] result = new double[2];
        if (Math.abs(averageGradeScale - 3.5) < 0.001 || Math.abs(averageGradeScale - 4.5) < 0.001) {
//            result[0] = getGradeScaleWhenHalf(averagePoints);
//            if (getGradeScaleFromPoints(averagePoints) > result[0])
//                result[1] = getMaxPointsFromGradeScale(result[0]);
//            else result[1]=getMinPointsFromGradeScale(result[0]);
            result[1] = (int) Math.round(averagePoints);
            result[0] = getGradeScaleFromPoints(result[1]);
        } else {
            if (getGradeScaleFromPoints(averagePoints) == Math.round(averageGradeScale)) {
                result[0] = (int) Math.round(averageGradeScale);
                result[1] = (int) Math.round(averagePoints);
            }
            if (getGradeScaleFromPoints(averagePoints) > Math.round(averageGradeScale)) {
                result[0] = (int) Math.round(averageGradeScale);
                result[1] = getMaxPointsFromGradeScale(averageGradeScale);
            }
            if (getGradeScaleFromPoints(averagePoints) < Math.round(averageGradeScale)) {
                result[0] = (int) Math.round(averageGradeScale);
                result[1] = getMinPointsFromGradeScale(averageGradeScale);
            }
        }

        return result;
    }

    public static int getGradeScaleFromPoints(double points) {
        if (points >= 90 && points <= 100) return 5;
        if (points >= 74 && points <= 89) return 4;
        if (points >= 60 && points <= 73) return 3;
        return 0;
    }

//    public static int getGradeScaleWhenHalf(double points) {
//        if (points >= 86 && points <= 100) return 5;
//        if (points >= 69 && points <= 85) return 4;
//        if (points >= 60 && points <= 68) return 3;
//        return 0;
//    }

    public int getMaxPointsFromGradeScale(double gradeScale) {
        if (Math.round(gradeScale) == 5) return 100;//impossible situation;
        if (Math.round(gradeScale) == 4) return 89;
        if (Math.round(gradeScale) == 3) return 73;
        return 0;
    }

    public int getMinPointsFromGradeScale(double gradeScale) {
        if (Math.round(gradeScale) == 5) return 90;
        if (Math.round(gradeScale) == 4) return 74;
        if (Math.round(gradeScale) == 3) return 60;//impossible situation;
        return 0;
    }
}

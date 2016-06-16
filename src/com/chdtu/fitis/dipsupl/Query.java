package com.chdtu.fitis.dipsupl;

import com.chdtu.fitis.dipsupl.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static com.chdtu.fitis.dipsupl.HibernateUtil.getSessionFactory;

/**
 * Created by Supreme on 13.06.2016.
 */
public class Query {

    public static final int FIRST_SEMESTER = 1;

    public static String buildGetGradesQuery(int studentId, int lastSemester, List<Integer> kindsOfKnowledgeControl) {

        String setOfKnowledgeControlKinds = makeStringIndexFromIntegerList(kindsOfKnowledgeControl);
        String resultQuery =
                "Select g From Grade g " +
                        "Join Subject subj On g.subjectId=subj.id " +
                        "Join Student st On g.studentId=st.id " +
                        "Join GroupSubject gs On g.subjectId=gs.subjectId and (gs.group=st.group) " +
                        "Where g.studentId=" + studentId + " " +
                        "and subj.knowledgeControlID in " + setOfKnowledgeControlKinds +
                        "and subj.name in (" +
                        "Select E.name FROM Subject E " +
                        "Join GroupSubject F  on F.subjectId=E.id " +
                        "Where  F.group=st.group and F.inDiplomaAddition=true" +
                        ") " +
                        " and subj.semester>=" + FIRST_SEMESTER +
                        " and subj.semester<=" + lastSemester +
                        " Order by subj.name";
        return resultQuery;
    }

    public static String makeStringIndexFromIntegerList(List<Integer> list) {
        String index = "(";
        for (int type : list) {
            index += type + ",";
        }
        if (list.size() > 0) {
            index = index.substring(0, index.length() - 1);
        }
        index += ")";
        return index;
    }

    public static ArrayList<ArrayList<Grade>> getGrades(int studentId) {
        Student student = getStudent(studentId);
        int lastSemester = student.getGroup().calculateLastSemester();

        ArrayList<Integer> knowledgeControlsTypes = new ArrayList<Integer>();
        ArrayList<ArrayList<Grade>> grades = new ArrayList<>();

        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            knowledgeControlsTypes.add(KindsOfKnowledgeControl.EXAM);
            knowledgeControlsTypes.add(KindsOfKnowledgeControl.TEST);
            knowledgeControlsTypes.add(KindsOfKnowledgeControl.DIFFERENTIATED_TEST);
            grades.add((ArrayList) session.createQuery(buildGetGradesQuery(studentId, lastSemester, knowledgeControlsTypes)).list());
            knowledgeControlsTypes.clear();

            knowledgeControlsTypes.add(KindsOfKnowledgeControl.COURSEWORK);
            knowledgeControlsTypes.add(KindsOfKnowledgeControl.COURSE_PROJECT);
            grades.add((ArrayList) session.createQuery(buildGetGradesQuery(studentId, lastSemester, knowledgeControlsTypes)).list());
            knowledgeControlsTypes.clear();

            knowledgeControlsTypes.add(KindsOfKnowledgeControl.PRACTICE);
            grades.add((ArrayList) session.createQuery(buildGetGradesQuery(studentId, lastSemester, knowledgeControlsTypes)).list());
            knowledgeControlsTypes.clear();

            knowledgeControlsTypes.add(KindsOfKnowledgeControl.FINAL_EXAMINATION);
            grades.add((ArrayList) session.createQuery(buildGetGradesQuery(studentId, lastSemester, knowledgeControlsTypes)).list());
            knowledgeControlsTypes.clear();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return grades;
    }

    public static Student getStudent(int studentId) {
        Student student = null;

        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            student = (Student) session.createQuery("FROM Student Where id=" + studentId).uniqueResult();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }

    public static Group getGroup(int groupId) {
        Group group = null;

        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            group = (Group) session.createQuery("FROM Group Where id=" + groupId).uniqueResult();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return group;
    }

    public static List<Department> getActiveDepartments() {
        List<Department> result = null;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            result = session.createQuery("FROM Department  Where accountable=true").list();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Group> getSelectedGroups(int selectedDepartmentId) {
        List<Group> result = null;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            result = session.createQuery(
                    "Select gr FROM Group gr " +
                            "Join Speciality sp on sp.id=gr.specialityId " +
                            "Join Department dep on dep.id=sp.departmentId " +
                            "Join CurrentYear cr on cr.currentYear>0 " +
                            "Where gr.active=true " +
                            "and not gr.name='оп-122' " +
                            "and gr.creationYear=cr.currentYear-3 " +
                            "and dep.id =" + selectedDepartmentId
            ).list();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Student> getSelectedStudents(int selectedGroupId) {
        List<Student> result = null;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            result = session.createQuery(
                    "FROM Student stud " +
                            "Where stud.inActive=true " +
                            "and stud.group.id=" + selectedGroupId + " " +
                            "order by stud.surname"
            ).list();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}

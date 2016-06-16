package com.chdtu.fitis.dipsupl.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.chdtu.fitis.dipsupl.Query.getSelectedStudents;

/**
 * Created by Supreme on 24.04.2016.
 */
@WebServlet("/GetStudents")
public class GetStudents extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SelectedItems.setGroupId(Integer.parseInt(request.getParameter("groupSelector")));
        SelectedItems.setStudents(getSelectedStudents(SelectedItems.getGroupId()));

        request.setAttribute("departments", SelectedItems.getDepartments());
        request.setAttribute("groups", SelectedItems.getGroups());
        request.setAttribute("students", SelectedItems.getStudents());

        request.setAttribute("selectedDepartment", SelectedItems.getDepartmentId());
        request.setAttribute("selectedGroup", SelectedItems.getGroupId());

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

package com.chdtu.fitis.dipsupl.servlet;

import com.chdtu.fitis.dipsupl.entity.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.chdtu.fitis.dipsupl.Query.getActiveDepartments;
import static com.chdtu.fitis.dipsupl.Query.getSelectedGroups;

/**
 * Created by Supreme on 24.04.2016.
 */
@WebServlet("/GetGroups")
public class GetGroups extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SelectedItems.setDepartmentId(Integer.parseInt(request.getParameter("departmentSelector")));
        SelectedItems.setGroups(getSelectedGroups(SelectedItems.getDepartmentId()));

        request.setAttribute("departments", SelectedItems.getDepartments());
        request.setAttribute("groups", SelectedItems.getGroups());

        request.setAttribute("selectedDepartment", SelectedItems.getDepartmentId());
        request.setAttribute("selectedGroup", SelectedItems.getGroupId());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SelectedItems.setDepartments(getActiveDepartments());
        SelectedItems.setDepartmentId(Department.DEPARTMENT_ID_FITIS);
        SelectedItems.setGroups(getSelectedGroups(SelectedItems.getDepartmentId()));

        request.setAttribute("departments", SelectedItems.getDepartments());
        request.setAttribute("groups", SelectedItems.getGroups());

        request.setAttribute("selectedDepartment", SelectedItems.getDepartmentId());
        request.setAttribute("selectedGroup", SelectedItems.getGroupId());

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}

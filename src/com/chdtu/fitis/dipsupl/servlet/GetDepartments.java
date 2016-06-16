package com.chdtu.fitis.dipsupl.servlet;


import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import static com.chdtu.fitis.dipsupl.Query.getActiveDepartments;

/**
 * Created by Supreme on 17.04.2016.
 */
@WebServlet("/GetDepartments")
public class GetDepartments extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        SelectedItems.setDepartments(getActiveDepartments());
        request.setAttribute("departments", SelectedItems.getDepartments());
        request.setAttribute("selectedDepartment", SelectedItems.getDepartmentId());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}

package com.chdtu.fitis.dipsupl.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.chdtu.fitis.dipsupl.docmanager.DocumentBuilder.createDocumentForGroupById;
import static com.chdtu.fitis.dipsupl.docmanager.DocumentBuilder.generateGroupPathById;


/**
 * Created by Supreme on 06.06.2016.
 */
@WebServlet("/ReportForGroup")
public class ReportForGroup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        createDocumentForGroupById(SelectedItems.getGroupId());
        SelectedItems.setGeneratedFilePath(generateGroupPathById(SelectedItems.getGroupId()));

        request.setAttribute("groups", SelectedItems.getGroups());
        request.setAttribute("departments", SelectedItems.getDepartments());

        request.setAttribute("selectedDepartment", SelectedItems.getDepartmentId());
        request.setAttribute("selectedGroup", SelectedItems.getGroupId());

        request.getRequestDispatcher("/GetDepartments").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

<%--
  Created by IntelliJ IDEA.
  User: Supreme
  Date: 17.04.2016
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Виписка оцінок</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="header">Diploma Supplement</div>
<div id="body">
    <form action="GetGroups" method="post">
        <p class="label">Факультет:</p>
        <select name="departmentSelector" onchange="this.form.submit()">
            <option selected disabled>Виберіть факультет</option>
            <c:forEach var="dep" items="${departments}">
                <c:choose>
                    <c:when test="${selectedDepartment == dep.id}">
                        <option value="${dep.id}" selected>${dep.abbreviation}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${dep.id}">${dep.abbreviation}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </form>
    <form action="GetStudents" method="post">
        <p class="label">Група:</p>
        <select name="groupSelector" onchange="this.form.submit()">
            <option selected disabled>Виберіть групу</option>
            <c:forEach var="group" items="${groups}">
                <c:choose>
                    <c:when test="${selectedGroup == group.id}">
                        <option value="${group.id}" selected>${group.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${group.id}">${group.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </form>
    <form method="post" action="/ReportForGroup" id="groupReportSubmit">
        <input type="submit" value="Звіт для групи">
    </form>
    <form action="ReportForSingleStudent" method="post">
        <p class="label">Студент:</p>
        <select name="studentSelector">
            <c:forEach var="student" items="${students}">
                <c:choose>
                    <c:when test="${selectedStudent == student.id}">
                        <option value="${student.id}" selected>${student.surname} ${student.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${student.id}">${student.surname} ${student.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input type="submit" id="studentReportSubmit" value="Звіт для студента">
    </form>
</div>
</body>
</html>

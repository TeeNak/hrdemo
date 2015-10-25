<%--
  Created by IntelliJ IDEA.
  User: teenak
  Date: 2015/02/01
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="job.jobList" /></title>

    <jsp:include page="fragments/staticFiles.jsp"/>

</head>
<body>
<h2><spring:message code="job.jobList"/></h2>
<a class="btn" href="new">New Job</a>
<table class="table table-bordered table-striped" style="width:600px;">
    <tr>
        <th>Key</th>
        <th>Code</th>
        <th>Name</th>
        <th></th>
    </tr>
    <c:forEach var="job" items="${jobList}">
        <tr>
            <td><b><c:out value="${job.key}"/></b></td>
            <td><b><c:out value="${job.code}"/></b></td>
            <td><b><c:out value="${job.name}"/></b></td>
            <td>
                <spring:url value="/secure/Job/{jobKey}/edit" var="editUrl">
                    <spring:param name="jobKey" value="${job.key}"/>
                </spring:url>
                <a href="${fn:escapeXml(editUrl)}">Edit This Record</a>
<%--
                &nbsp;
                <spring:url value="/Job/{jobKey}/delete" var="deleteUrl">
                    <spring:param name="jobKey" value="${job.key}"/>
                </spring:url>
                <a href="${fn:escapeXml(deleteUrl)}">Delete This Record</a>
--%>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

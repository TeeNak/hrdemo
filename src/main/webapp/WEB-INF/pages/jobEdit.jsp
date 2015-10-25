<%--
  Created by IntelliJ IDEA.
  User: teenak
  Date: 2015/01/25
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Job Form</title>

  <jsp:include page="fragments/staticFiles.jsp"/>

  <style>
    .error { color: red; }
    a[disabled] { pointer-events: none; }
  </style>

</head>
<body>
  <h2>

    <c:if test="${job.key == null}">
      <spring:message code="general.new" >
        <spring:argument>
          <spring:message code="job.job"/>
        </spring:argument>
      </spring:message>
    </c:if>
    <c:if test="${job.key != null}">
      <spring:message code="job.job"/>
    </c:if>
  </h2>

  <spring:url value="/secure/Job/{jobKey}/delete" var="deleteUrl">
    <spring:param name="jobKey" value="${job.key}"/>
  </spring:url>

  <c:if test="${job.key != null}">
    <a class="btn" href="./edit" >Refresh</a>
    <a class="btn" href="#" onclick="var f = document.getElementById('editForm');f.action='${fn:escapeXml(deleteUrl)}';f.submit();">Delete This Record</a>
  </c:if>

  <c:if test="${deleting == null}">
    <c:set var="read" value="false"/>
    <c:set var="disabled" value=''/>
  </c:if>
  <c:if test="${deleting != null}">
    <c:set var="read" value="true"/>
    <c:set var="disabled" value='disabled="true"'/>
  </c:if>

  <form:form id="editForm" method="post" commandName="JobUpdate"
             modelAttribute="job">
    <form:hidden path="version"/>
    <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td align="right" width="20%"></td>
        <td colspan="2">
          <form:errors path="*" cssClass="error"/>
          <div class="errorblock error">${errorMessage}</div>
          <div class="messageblock">${feedbackMessage}</div>
        </td>
      </tr>
      <tr>
        <td align="right" width="20%">Key :</td>
        <td width="20%">
          <form:input readonly="true" path="key"/>
        </td>
        <td class="error" width="60%">
        </td>
      </tr>
      <tr>
        <td align="right" width="20%">Code :</td>
        <td width="20%">
          <form:input readonly="${read}" path="code" />
        </td>
        <td class="error" width="60%">
          <form:errors path="code" cssClass="error"/>
        </td>
      </tr>
      <tr>
        <td align="right" width="20%">Name :</td>
        <td width="20%">
          <form:input readonly="${read}" path="name"/>
        </td>
        <td class="error" width="60%">
          <form:errors path="name" cssClass="error"/>
        </td>
      </tr>
    </table>
    <a class="btn" ${disabled} href="#" onclick="document.getElementById('editForm').submit();">OK</a>

    <spring:url value="/secure/Job/list" var="listUrl"/>
    <a class="btn" href="${fn:escapeXml(listUrl)}" >Cancel</a>
  </form:form>
</body>
</html>

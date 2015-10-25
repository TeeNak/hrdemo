<%--
  Created by IntelliJ IDEA.
  User: teenak
  Date: 2015/02/05
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>

    <jsp:include page="/WEB-INF/pages/fragments/staticFiles.jsp"/>

</head>
<body>
<div class="container">
    <h2>Something happened...</h2>

    <!--
    <p>${exception.message}</p>
    -->
    <pre><jsp:scriptlet>out.println(exception.getClass().getName());</jsp:scriptlet></pre>
    <p>Exception message: ${exception.message}</p>
    <pre><jsp:scriptlet>exception.printStackTrace(new java.io.PrintWriter(out));</jsp:scriptlet>
    </pre>

</div>
</body>
</html>

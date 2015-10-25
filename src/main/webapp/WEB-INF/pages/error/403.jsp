<%--
  Created by IntelliJ IDEA.
  User: teenak77
  Date: 2015/04/05
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Forbidden</title>

  <jsp:include page="/WEB-INF/pages/fragments/staticFiles.jsp"/>

</head>
<body>
  <h2>You are not authorized to view this resource.</h2>
  <p>You may not visit this page because of:</p><br/>
  <p>
    1. An out-of-date bookmark/favorite<br/>
    2. A search engine that has an out-of-date listing for this site<br/>
    3. A mistyped address<br/>
    4. You have no access to this page<br/>
    5. An error has occurred while processing your request<br/>
  </p>

</body>
</html>

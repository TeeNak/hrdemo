<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

  <c:if test="${param.error != null}">
      Your login attempt was not successful, try again.<br/><br/>
      Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
  </c:if>


  <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
    <table>
      <tr><td>User:</td><td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td></tr>
      <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>

      <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
      <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
    </table>
    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
  </form>

</body>
</html>

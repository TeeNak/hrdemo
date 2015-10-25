<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h1>${message}</h1>
	<p>Username : <security:authentication property="principal.username"/></p>
	<p><a href="secure/Job/list">Job list</a></p><br/>

	<c:url var="logoutUrl" value="j_spring_security_logout"/>
	<form action="${logoutUrl}" method="post">
		<input type="submit" value="Logout" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>
</html>
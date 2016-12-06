<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Spring Security - Hello world!</h1>

	<spring:url value="/secure/" var="secureUrl" />
	<a href="${secureUrl}" title="Secure">Secure</a>
	<br />

	<spring:url value="/admin/" var="adminUrl" />
	<a href="${adminUrl}" title="Admin">Admin</a>

	<br />
	<br />
	
	<sec:authorize access="isAuthenticated()">
   		<a href="<c:url value="/logout"/>">Logout</a>
	</sec:authorize>

</body>
</html>

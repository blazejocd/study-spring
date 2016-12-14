<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" 
          href="<c:url value="/CSS/style.css" />" >
  </head>
  <body>
    <h1><s:message code="spittr.register" /></h1>
	<form:form method="POST" commandName="spitter">
	<form:errors path="*" element="div" cssClass="error" />
	Imię: <form:input path="firstName" /><br/>
	Nazwisko: <form:input path="lastName"/><br/>
	Email: <form:input path="email" /><br />
	Nazwa użytkownika: <form:input path="username" /><br />
	Hasło: <form:password path="password" /><br />
	<input type="submit" value="Zarejestruj się" />
	</form:form>
  </body>
</html>

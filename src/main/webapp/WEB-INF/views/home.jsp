<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" 
          type="text/css" 
          href="<c:url value="CSS/style.css" />" >
  </head>
  <body>
    <h1><s:message code="spittr.welcome" /></h1>

    <a href="<c:url value="/spittles" />"><s:message code="spittr.spittles"/></a> | 
    <a href="<c:url value="/spitter/register" />"><s:message code="spittr.register" /></a>
  </body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><c:out value="${errorTitle}" /></title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

  <link rel="stylesheet" href="<c:url value="/static/css/403View.css"/>">
</head>
<body>
<div class="container">
  <h1 class="mb-4"><c:out value="${errorTitle}" /></h1>
  <p class="mb-4"><c:out value="${errorSubtitle}" /></p>
  <a href="<c:url value="/"/>" class="btn btn-primary mt-3" style="background-color: #2B3B2B;border-color: #2B3B2B"><spring:message code="403.button" /></a>
</div>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 4/2/23
  Time: 7:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Info</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/static/css/main.css"/>">
</head>
<body class="lendView">

    <jsp:include page="../components/navBar.jsp"/>
    <div class="container my-5">
        <h1 class="text-center mb-3"><c:out value="${name}"/></h1>
        <div class="d-flex">
            <img src="https://i.pinimg.com/originals/d4/2e/d7/d42ed7bf30a4c1a6a201565f0bc61190.jpg" class="w-25 h-25 mx-5" alt="Book cover">
            <div class="mx-4">
                <h2>Información</h2>
                <hr class="hr" />
                <div>
                    <p style="margin-bottom: -5px">Título</p>
                    <h3><c:out value="${name}"/></h3>

                    <p style="margin-bottom: -5px">Autor</p>
                    <h3><c:out value="${author}"/></h3>
                
                    <p style="margin-bottom: -5px">Idioma</p>
                    <h3><c:out value="${language}"/></h3>
                
                    <p style="margin-bottom: -5px">ISBN</p>
                    <h3><c:out value="${isbn}"/></h3>

                    <p style="margin-bottom: -5px">Estado</p>
                    <h3><c:out value="${physicalCondition}"/></h3>
                </div>
            </div>
            <div class="vr" style="color: #849ba4; width: 2px"></div>
            <div class="mx-4">
                <h2>Ubicación</h2>
                <hr class="hr" />
                <div>
                    <p style="margin-bottom: -5px">Código postal</p>
                    <h3><c:out value="${locationPC}"/></h3>
                    
                    <p style="margin-bottom: -5px">Localidad</p>
                    <h3><c:out value="${location}"/></h3>
                    
                    <p style="margin-bottom: -5px">Provincia</p>
                    <h3><c:out value="${province}"/></h3>

                    <p style="margin-bottom: -5px">País</p>
                    <h3><c:out value="${country}"/></h3>
                </div>
            </div>
        </div>
        <div class="text-center mt-5">
            <button type="button" class="btn btn-primary">Pedir prestado este libro!</button>
        </div>
    </div>
</body>
</html>

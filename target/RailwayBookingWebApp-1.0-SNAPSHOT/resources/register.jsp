<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Railway Booking</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">

    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/bootstrap.css"/>"/>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/bootstrap-theme.css"/>"/>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/css/style.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <fmt:setBundle basename="login" var="login"/>
    <fmt:setBundle basename="message" var="message"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Railway Booking</a>
        </div>
    </div>
</nav>
<div class="row">
    <div class="col-md-5"></div>
    <div class="col-md-2" style="text-align: center;">
        <form style="padding-top: 100%; text-align: center" method="post" action="RailwaySystem/login" class="center-block">
            <input type="email" placeholder="<fmt:message key="login.emailRegister" bundle="${login}"/>" name="email"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
                   value="${fn:escapeXml(email)}"
            />
            <input type="password" placeholder="<fmt:message key="login.passwordRegister" bundle="${login}"/>"
                   minlength="8" name="password" required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
                   value="${fn:escapeXml(password)}"
            />
            <input type="text" placeholder="<fmt:message key="login.name" bundle="${login}"/>" name="name"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
                   value="${fn:escapeXml(name)}"
            />
            <input type="text" placeholder="<fmt:message key="login.surname" bundle="${login}"/>" name="surname"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
                   value="${fn:escapeXml(surname)}"
            />
            <input type="tel" name="phone" placeholder="<fmt:message key="login.tel" bundle="${login}"/>"
                   required
                   class="form-control"
                   style="width: 100%; margin-bottom: 10px"
                   value="${fn:escapeXml(phone)}"
            />
            <c:if test="${requestScope.errorMessage != null}">
                <h4><fmt:message key="message.containsEmail" bundle="${message}"/></h4>
            </c:if>
            <button class="btn btn-primary" type="submit" value="register" name="command"><fmt:message key="login.signUp"
                                                                                            bundle="${login}"/></button>
        </form>
    </div>
    <div class="col-md-5"></div>
</div>
</body>
</html>

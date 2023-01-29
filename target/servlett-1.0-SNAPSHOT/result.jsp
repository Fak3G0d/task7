<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
    <body>
        <div class="container-fluid col-4" style="margin-top:100px">
            <c:if test="${not empty status}">
                <c:if test="${status eq 'connect error'}">
                    <div class="alert alert-danger" role="alert">
                      Ошибка подключения к серверу!
                    </div>
                </c:if>
                <c:if test="${status eq 'no task'}">
                    <div class="alert alert-warning" role="alert">
                      Такой задачи нет!
                    </div>
                </c:if>
                <c:if test="${status eq 'not ready'}">
                    <div class="alert alert-warning" role="alert">
                      Задача еще не готова!
                    </div>
                </c:if>
            </c:if>
            <h3>Введите номер вашей задачи:</h3>
            <form action="${pageContext.request.contextPath}/result" method="GET">
                <label for="id" class="form-label"> ID: </label> <input class="form-control" id="id" type="number"
                                    min="1" name="id" required> <br>
                <button class="btn btn-primary" type="submit" name="submit">Submit</button>
            </form>
            <c:if test="${status eq 'success'}">
                Результаты:
                <ul class="list-group">
                    <c:forEach var="num" items="${results}">
                        <li class="list-group-item"><c:out value="${num}"/></li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </body>
</html>
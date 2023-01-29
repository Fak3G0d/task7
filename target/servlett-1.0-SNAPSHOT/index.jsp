<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
    <body>
        <div class="container-fluid col-4" style="margin-top:100px">
            <c:if test="${not empty submitResult}">
                <c:if test="${submitResult eq 'success'}">
                    <div class="alert alert-success" role="alert">
                      Задача добавлена на сервер!
                    </div>
                </c:if>
                <c:if test="${submitResult eq 'error'}">
                    <div class="alert alert-danger" role="alert">
                      Что-то пошло не так!
                    </div>
                </c:if>
            </c:if>
            <h3>Введите начало и конец отрезка:</h3>
            <form action="${pageContext.request.contextPath}" method="GET">
                <label for="L" class="form-label"> L: </label> <input class="form-control" id="L" type="number"
                    min="0" name="L" required> <br>
                <label for="R" class="form-label"> R: </label> <input class="form-control" id="R" type="number"
                    min="0" name="R" required> <br>
                <button class="btn btn-primary" type="submit" name="submit">Submit</button>
            </form>
        </div>
    </body>
</html>
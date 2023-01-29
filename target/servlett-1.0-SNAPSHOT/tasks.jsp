<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
    <body>
        <div class="container-fluid col-4" style="margin-top:100px">
            <c:if test="${connectError}">
                <div class="alert alert-danger" role="alert">
                  Ошибка подключения к серверу!
                </div>
            </c:if>
            <c:if test="${empty connectError}">
                <table class="table">
                  <thead class="thead-light">
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">L</th>
                      <th scope="col">R</th>
                      <th scope="col">Error?</th>
                      <th scope="col">Status</th>
                    </tr>
                  </thead>
                  <tbody>
                      <c:forEach var="task" items="${tasks}">
                        <tr>
                          <th scope="row"><c:out value="${task[0]}"/></th>
                          <td><c:out value="${task[1]}"/></td>
                          <td><c:out value="${task[2]}"/></td>
                          <td><c:out value="${task[3]}"/></td>
                          <td><c:out value="${task[4]}"/></td>
                        </tr>
                      </c:forEach>
                  </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
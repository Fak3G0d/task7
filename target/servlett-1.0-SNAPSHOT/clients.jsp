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
                      <th scope="col">State</th>
                      <th scope="col">Action</th>
                    </tr>
                  </thead>
                  <tbody>
                      <c:forEach var="client" items="${clients}">
                        <tr>
                          <th scope="row"><c:out value="${client[0]}"/></th>
                          <td scope="row"><c:out value="${client[1]}"/></td>
                          <td>
                            <form action="${pageContext.request.contextPath}/clients" method="GET">
                                <button class="btn btn-danger" name="disconnect" value="${client[0]}">Disconnect</button>
                            </form>
                          </td>
                        </tr>
                      </c:forEach>
                  </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/headerApp.jsp"/>
<div class="m-4 p-3 width-medium">
    <div class="m-4 p-3 border-dashed view-height">

        <div class="row border-bottom border-3 p-1 m-1">
            <div class="col noPadding">
                <h3 class="color-header text-uppercase">LISTA UŻYTKOWNIKÓW</h3>
            </div>
<%--            <div class="col d-flex justify-content-end mb-2 noPadding">--%>
<%--                <a href="#" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>--%>
<%--            </div>--%>
        </div>

        <div class="schedules-content">
            <table class="table">
                <thead>
                <tr class="d-flex">
                    <th class="col-1">ID</th>
                    <th class="col-3">IMIĘ</th>
                    <th class="col-6">NAZWISKO</th>
                    <th class="col-2 center">AKCJE</th>
                </tr>
                </thead>
                <tbody class="text-color-lighter">
                <c:forEach var="user" items="${users}">
                    <c:if test="${user.superadmin != 1}">
                        <tr class="d-flex">
                            <td class="col-1">${user.id}</td>
                            <td class="col-3">${user.firstName}</td>
                            <td class="col-6">${user.lastName}</td>
                            <td class="col-2 center">
                                <c:choose>
                                    <c:when test="${user.enable == 1}">
                                        <a href="<c:url value="disableUser?id=${user.id}" />" class="btn btn-danger rounded-0 text-light m-1">Blokuj</a>
                                    </c:when>
                                    <c:otherwise>
<%--                                        btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4--%>
                                        <a href="<c:url value="enableUser?id=${user.id}" />" class="btn btn-success rounded-0 text-light m-1">Odblokuj</a>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
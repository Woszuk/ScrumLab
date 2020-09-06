<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../headerApp.jsp"/>
        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="<c:url value="/app/schedules"/>" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text"><c:out value="${plan.name}" /></p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">
                                    <c:out value="${plan.description}" />
                                </p>
                            </div>
                        </div>
                    </div>
                    <c:forEach var="dayName" items="${dayName}">
                    <table class="table">
                        <table class="table">
                            <thead>
                            <tr class="d-flex">
                                <th class="col-2">${dayName}</th>
                                <th class="col-8"></th>
                                <th class="col-2"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="recipePlan" items="${recipePlanDetails}">
                                <c:if test="${recipePlan.dayName == dayName}">
                                    <tr class="d-flex">
                                        <td class="col-2">${recipePlan.mealName}</td>
                                        <td class="col-8">${recipePlan.recipeName}</td>
                                        <td class="col-2">
                                            <a href="<c:url value="/app/delete?id=${recipePlan.id}&name=recipePlan&planId=${recipePlan.planId}"/> " class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                            <a href="<c:url value="/app/recipeDetails?id=${recipePlan.recipeId}"/>" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                        </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../footer.jsp" />
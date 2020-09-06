<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../headerApp.jsp"/>
<div class="m-4 p-4 width-medium">
    <div class="dashboard-header m-4">
        <div class="dashboard-menu">
            <div class="menu-item border-dashed">
                <a href="<c:url value="/app/addRecipe" />">
                    <i class="far fa-plus-square icon-plus-square"></i>
                    <span class="title">dodaj przepis</span>
                </a>
            </div>
            <div class="menu-item border-dashed">
                <a href="<c:url value="/app/addSchedule" />">
                    <i class="far fa-plus-square icon-plus-square"></i>
                    <span class="title">dodaj plan</span>
                </a>
            </div>
            <div class="menu-item border-dashed">
                <a href="<c:url value="/app/recipePlanAdd"/>">
                    <i class="far fa-plus-square icon-plus-square"></i>
                    <span class="title">dodaj przepis do planu</span>
                </a>
            </div>
        </div>

        <div class="dashboard-alerts">
            <div class="alert-item alert-info">
                <i class="fas icon-circle fa-info-circle"></i>
                <span class="font-weight-bold">Liczba przepisów: ${sumRecipes}</span>
            </div>
            <div class="alert-item alert-light">
                <i class="far icon-calendar fa-calendar-alt"></i>
                <span class="font-weight-bold">Liczba planów: ${sumPlans}</span>
            </div>
        </div>
    </div>
    <div class="m-4 p-4 border-dashed">
        <c:choose>
            <c:when test="${sumPlans == 0}">
                <h2 class="dashboard-content-title">
                    <span>Ostatnio dodany plan:</span> Brak
                </h2>
            </c:when>
            <c:when test="${sumPlans != 0}">
            <h2 class="dashboard-content-title">
                <span>Ostatnio dodany plan:</span> ${namePlan}
            </h2>
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
                                    <a href="<c:url value="/app/recipeDetails?id=${recipePlan.recipeId}"/>" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                </c:forEach>
            </c:when>
        </c:choose>
    </div>
</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../headerApp.jsp"/>
<div class="m-4 p-4 width-medium">
    <div class="m-4 p-4 border-dashed">
        <h2 class="dashboard-content-title">
            <span>Czy na pewno chcesz usunąć?</span>
        </h2>
        <div class="dashboard-content-title">
            <c:choose>
                <c:when test="${!empty plan}">
                    <span><a href="<c:url value="/app/deleteSchedule?id=${id}"/> " class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">OK</a></span>
                    <span><a href="<c:url value="/app/schedules"/>" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Anuluj</a></span>
                </c:when>
                <c:when test="${!empty recipe}">
                    <span><a href="<c:url value="/app/deleteRecipe?id=${id}"/>" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">OK</a></span>
                    <span><a href="<c:url value="/app/recipes"/>" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Anuluj</a></span>
                </c:when>
                <c:when test="${!empty recipePlan}">
                    <span><a href="<c:url value="/app/deleteRecipePlan?id=${id}&planId=${planId}"/>" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">OK</a></span>
                    <span><a href="<c:url value="/app/detailsSchedule?id=${planId}"/>" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Anuluj</a></span>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
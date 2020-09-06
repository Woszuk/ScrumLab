<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/headerApp.jsp"/>
        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="schedules-content">
                    <form method="post", action="/app/recipePlanAdd">
                        <div class="row border-bottom border-3 p-1 m-1">
                            <div class="col noPadding">
                                <h3 class="color-header text-uppercase">DODAJ PRZEPIS DO PLANU</h3>
                            </div>
                            <div class="col d-flex justify-content-end mb-2 noPadding">
                                <button type="submit" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button>
                            </div>
                        </div>
                        <c:if test="${!empty cookie.errorRecipePlanAdd}">
                            <h2 class="dashboard-content-title">
                                <span  style="color: red; font-size: 13px">Brak danych do wprowadzenia</span>
                            </h2>
                        </c:if>
                        <div class="form-group row">
                            <label for="choosePlan" class="col-sm-2 label-size col-form-label">
                                Wybierz plan
                            </label>
                            <div class="col-sm-3">
                                <select class="form-control" id="choosePlan" name="plan">
                                    <c:forEach items="${plans}" var="plan" >
                                        <option>${plan.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="meal" class="col-sm-2 label-size col-form-label">
                                Nazwa posiłku
                            </label>
                            <div class="col-sm-2">
                                <select class="form-control" id="meal" name="mealName">
                                    <c:forEach var="mealName" items="${mealNames}">
                                        <option>${mealName.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="recipie" class="col-sm-2 label-size col-form-label">
                                Przepis
                            </label>
                            <div class="col-sm-4">
                                <select class="form-control" id="recipie" name="recipe">
                                    <c:forEach var="recipe" items="${recipes}">
                                        <option>${recipe.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="day" class="col-sm-2 label-size col-form-label">
                                Dzień
                            </label>
                            <div class="col-sm-2">
                                <select class="form-control" id="day" name="day">
                                    <c:forEach items="${dayNames}" var="dayName">
                                        <option>${dayName.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../footer.jsp" />
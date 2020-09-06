<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/headerApp.jsp"/>
<div class="m-4 p-3 width-medium text-color-darker">
    <div class="dashboard-content border-dashed p-3 m-4 view-height">
        <div class="mt-4 ml-4 mr-4">
            <!-- fix action, method -->
            <!-- add name attribute for all inputs -->
            <form method="post" , action="/app/editRecipe">
                <div class="row border-bottom border-3">
                    <div class="col"><h3 class="color-header text-uppercase">Edycja przepisu</h3></div>
                    <div class="col d-flex justify-content-end mb-2">
                        <button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button>
                    </div>
                </div>
                <c:if test="${!empty cookie.errorEditRecipe}">
                    <h2 class="dashboard-content-title">
                        <span  style="color: red; font-size: 13px">Pola nie mogą być puste</span>
                    </h2>
                </c:if>
                <table class="table borderless">
                    <tbody>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Nazwa Przepisu</th>
                        <td class="col-7">
                            <input class="w-100 p-1" name="name" value="${recipe.name}">
                        </td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Opis przepisu</th>
                        <td class="col-7"><textarea class="w-100 p-1" rows="5" name="description"><c:out
                                value="${recipe.description}"/></textarea></td>
                    </tr>
                    <tr class="d-flex">
                        <th scope="row" class="col-2">Przygotowanie (minuty)</th>
                        <td class="col-3">
                            <input class="p-1" type="number" name="preparationTime"
                                   value="${recipe.preparationTime}">
                        </td>
                    </tr>
                    <input type="hidden" value="${recipe.id}" name="id" />
                    </tbody>
                </table>

                <div class="row d-flex">
                    <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Sposób przygotowania</h3></div>
                    <div class="col-2"></div>
                    <div class="col-5 border-bottom border-3"><h3 class="text-uppercase">Składniki</h3></div>
                </div>
                <div class="row d-flex">
                    <div class="col-5 p-4">
                        <textarea class="w-100 p-1" rows="10" name="preparation"><c:out value="${recipe.preparation}"/></textarea>
                    </div>
                    <div class="col-2"></div>

                    <div class="col-5 p-4">
                        <textarea class="w-100 p-1" rows="10" name="ingredients"><c:forEach items="${ingredients}" var="ingredient">${ingredient}</c:forEach></textarea>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/header.jsp" />
<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <div class="border-dashed view-height">
            <div class="container w-25">
                <form class="padding-small text-center" action="login" method="post">
                    <h1 class="text-color-darker">Logowanie</h1>
                    <c:choose>
                        <c:when test="${!empty error}">
                            <p style="color: red; font-size: 13px">Błędny email, albo hasło</p>
                        </c:when>
                        <c:when test="${!empty disable}">
                            <p style="color: red; font-size: 13px">Twoje konto jest zablokowane</p>
                        </c:when>
                    </c:choose>
                    <div class="form-group">
                        <input type="text" class="form-control" id="email" name="email" placeholder="podaj adres email">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="podaj hasło">
                    </div>
                    <button class="btn btn-color rounded-0" type="submit">Zaloguj</button>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="/WEB-INF/footer.jsp" />
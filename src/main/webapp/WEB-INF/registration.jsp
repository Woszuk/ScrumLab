<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/header.jsp"/>
<section class="dashboard-section">
    <div class="container pt-4 pb-4">
        <div class="border-dashed view-height">
            <div class="container w-25">
                <form class="padding-small text-center" method="post" action="register">
                    <h1 class="text-color-darker">Rejestracja</h1>

                    <c:choose>
                        <c:when test="${!empty error}">
                            <p style="color: red; font-size: 13px">Pola nie mogą być puste</p>
                        </c:when>
                        <c:when test="${!empty different && !empty duplicate}">
                            <p style="color: red; font-size: 13px">Podany email już istnieje</p>
                            <p style="color: red; font-size: 13px">Hasła nie są identyczne lub nie zawierają
                                przynajmniej 5 znaków</p>
                        </c:when>
                        <c:when test="${!empty duplicate}">
                            <p style="color: red; font-size: 13px">Podany email już istnieje</p>
                        </c:when>
                        <c:when test="${!empty different}">
                            <p style="color: red; font-size: 13px">Hasła nie są identyczne lub nie zawierają
                                przynajmniej 5 znaków</p>
                        </c:when>
                    </c:choose>


                    <div class="form-group">
                        <input type="text" class="form-control" id="name" name="name" placeholder="podaj imię">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="surname" name="surname"
                               placeholder="podaj nazwisko">
                    </div>
                    <div class="form-group">

                        <input type="text" class="form-control" id="email" name="email" placeholder="podaj email">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="podaj hasło">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" id="repassword" name="password2"
                               placeholder="powtórz hasło">
                    </div>
                    <button class="btn btn-color rounded-0" type="submit">Zarejestruj</button>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="footer.jsp"/>
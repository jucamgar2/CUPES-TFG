<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Editar mi perfil">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'dd/mm/yy' });
            });
        </script>
        <div class="block">
                <h1 class="title">Editar mi perfil</h1>
                <c:if test="${not empty errors}">
                    <div id="error-notifications">
                        <c:forEach items="${errors}" var="error">
                            <div class="error-notification">
                                <span>${error}</span>
                                <button class="close-button">×</button>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <form:form modelAttribute="player">
                    <div>
                        <p class="game">Nombre de usuario: <c:out value="${player.getUsername()}"/></p>
                    </div>
                    <div class="block">
                        <div>
                            <label for="name">Nombre:</label>
                        </div>
                        <div>
                            <input class="input-large" type="text" id="name" name="name" value="<c:out value='${player.getName()}'/>" required>
                        </div>
                        <div>
                            <label for="mail">Email:</label>
                        </div>
                        <div>
                            <input class="input-large" type="text" id="mail" name="mail" value="<c:out value='${player.getMail()}'/>" required>
                        </div>

                        <div>
                            <label for="birthDate">Fecha de nacimiento:</label>
                        </div>
                        <div>
                            <input class="input-large" type="text" id="birthDate" name="birthDate" value="<c:out value='${player.getBirthDate()}'/>" required>
                        </div>
                    </div>
                    <div class="block">
                        <button class="buttom" type="submit">Editar</button>
                    </div>
                </form:form>

        <script src="/js/error_script.js"></script>
        <script src="/js/show_pasword.js"></script>
    </div>
</CUPES:layout>
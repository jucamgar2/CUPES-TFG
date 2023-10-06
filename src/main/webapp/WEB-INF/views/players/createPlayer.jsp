<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <div class="register">
                <h1 class="register">Registrarse</h1>
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
                        <label for="username">Usuario:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="username" name="username" required>
                    </div>
                    <div class="errors" style="color:red">
                        <c:out value="${existsError}"/>
                        <c:out value="${usernameError}"/>
                    </div>
                    <br>
                    <div>
                        <label for="password">Contraseña:</label>
                    </div>
                    <div>
                        <input type="text" class ="input-large" id="password" name="password" required>
                    </div>
                    <div class="errors" style="color:red">
                        <c:out value="${passwordError}"/>
                    </div>
                    <br>
                    <button class="buttom" type="submit">Registrarme</button>
                </form:form>
                <div class="registerInfo">
                    <h2>Registrate para poder tener acceso al modo de juego online, donde podrás competir contra jugadores de todo el mundo</h2>
                    <h2>Aunque si no quieres, puedes seguir jugando al modo un jugador para seguir practicando y adquiriendo más conocimiento</h2>
                    <a class="buttom" href="/game/new">Comenzar una partida para un jugador</a>
                </div>
            </div>
        </div>
        <script src="/js/error_script.js"></script>
    </div>
</CUPES:layout>
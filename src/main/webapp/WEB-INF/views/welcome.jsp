<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <c:forEach items="${errormsg}" var="error">
                <div class="error-notification">
                    <span>${error}</span>
                    <button class="close-button">×</button>
                </div>
            </c:forEach>
            <h1 class="tituloPrincipal">Bienvenido a</h1>
            <div class="animation">
                <div id="animated-text"></div>
            </div>
            <div class="welcome">
                <h2>CUPES, Con un Pedazo es Suficiente será tu nuevo juego de adivinanzas visuales preferido.</h2>
                <h2>En esta aplicación podrás demostrar tus conocimientos.</h2>
                <sec:authorize access="!isAuthenticated()">
                    <h2>Si es la primera vez que entras a CUPES, te recomendamos que te registres:</h2>
                    <a href="/players/new" class="buttom">Registrarse</a>
                    <h2>Aunque realmente no es necesario para probar nuestro modo de juego para un jugador:</h2>
                    <a href="/game/new" class="buttom">Probar modo para un jugador</a>
                    <h2>Otra opción si no quieres registrarte es que compitas con un amigo en el modo local:</h2>
                    <a href="/game/localGame/new" class="buttom">Probar 1vs1 local</a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <h2>Prueba desde ya nuestro modo competitivo en linea donde tienes la opción de crear una partida o unirte a una partida ya creada:</h2>
                    <a href="/game/onlineGame/new" class="buttom">Crear partida 1vs1 en línea</a>
                    <a href="/game/onlineGame/join" class="buttom">Unirse a una partida 1vs1 en línea</a>
                </sec:authorize>
                <script src="/js/error_script.js"></script>
            </div>
        </div>
    </div>
</CUPES:layout>
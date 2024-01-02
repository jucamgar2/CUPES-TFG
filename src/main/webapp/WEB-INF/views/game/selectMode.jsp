<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Seleccionar modo">
        <div class="block">
            <div class="selectGameMode">
                <h1 class="title">Selecciona el modo de juego al que quieras jugar</h1>
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
                <h2>En el modo un jugador, tendrás tres intentos para adivinar un escudo</h2>
                <a class="buttom" href="/game/new">Un jugador</a>
                <h2>En el modo uno contra uno local, podrás jugar con otra persona en tu mismo dispositivo y cada uno tendrá que adivinar un escudo en el menor numero posible de intentos y de tiempo</h2>
                <a class="buttom" href="/game/localGame/new">1vs1 local</a>
                <h2>En el modo en linea jugaras contra otra persona y ambos tendreis que adivinar un escudo en el menor número de intentos posible, tardando el menor tiempo posible</h2>
                <a class="buttom" href="/game/onlineGame/new">Crear una partida 1vs1 en linea</a>
                <a class="buttom" href="/game/onlineGame/join">Unirse a una partida 1vs1 en linea</a>
                <h2>Si tienes alguna duda sobre como jugar, puedes consultar el manual</h2>
                <a href="/manual" class="buttom">Ir al manual</a>
            </div>
        </div>
    <script src="/js/error_script.js"></script>
</CUPES:layout>
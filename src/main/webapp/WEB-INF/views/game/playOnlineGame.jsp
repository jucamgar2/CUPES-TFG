<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="game">
    <div class="block">
            <h1 class="game">¿Es suficiente este pedazo para que adivines el escudo?</h1>
            <c:if test="${error}">
                <div id="error-notifications">
                    <div class="error-notification">
                        <span>¡Lo siento! Fallaste en tu anterior intento</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>
            <c:if test="${succes}">
                <div id="succes">
                    <div class="succes-notification">
                        <span>¡Enhorabuena! Acertaste en tu anterior intento</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>
            <div class="center-items">
                <div class="image-container" style="position: relative;">
                    <img src=<c:out value="${imageUrl}"/> style="width: 500px;height: 500px;border-color: black;box-shadow: 0 0 10px rgba(0, 0, 0);background-color: white;">
                    ${fullImageStyle}
                   
                </div>
            </div>

            <c:if test="${principal.getName() == game.getPlayer1().getUsername()}">
                <p class="game">Tienes que adivinar un total de 3 escudos y llevas <c:out value="${game.getCurrentPlayer1Image()}"/> </p>
                <p class="game">Intentos: <c:out value="${game.getPlayer1Shifts()}"/></p>
            </c:if>
            <c:if test="${principal.getName() == game.getPlayer2().getUsername()}">
                <p class="game">Tienes que adivinar un total de 3 escudos y llevas <c:out value="${game.getCurrentPlayer2Image()}"/> </p>
                <p class="game">Intentos: <c:out value="${game.getPlayer2Shifts()}"/></p>
            </c:if>

            
            

            
            <form:form modelAttribute="logo">
                <div class="logoSelector">
                    <div>
                        <form:label path="name">¿De quién es este escudo?</form:label>
                    </div>
                    <div>
                        <input class="input-large" name="name"  path="name" type="text" id="autocompleteInput" list="logos" autocomplete="off">
                        <datalist id="logos"></datalist>
                    </div>
                    <div>
                        <button  class="buttom" type="submit" href="/onlineGame/play/<c:out value='${game.id}'/>">Enviar</button>
                    </div>
                </div>
            </form:form>

            <div class="block">
                    <a class="buttom-danger" href ="/game/onlineGame/leave/<c:out value='${game.id}'/>">Abandonar</a>
            </div>
                

                <script src="/js/autocomplete.js"></script>
                <script src="/js/error_script.js"></script>
    </div>
</CUPES:layout>
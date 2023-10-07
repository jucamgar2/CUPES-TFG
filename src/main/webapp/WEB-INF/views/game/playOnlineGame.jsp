<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="game">
    <div class="doBody">
        <div class="theBody">
            <h1 class="game">¿Es suficieste este pedazo para que adivines el escudo?</h1>
            <div class="imageContainer">
                <div class="gameImage">
                    <img class="game">
                </div>
            </div>

            <c:if test="${principal.getName() == game.getPlayer1().getUsername()}">
                <p class="game">Tienes que adivinar un total de 3 escudos y llevas ${game.getPlayer1Succes()} </p>
            </c:if>
            <c:if test="${principal.getName() == game.getPlayer2().getUsername()}">
                <p class="game">Tienes que adivinar un total de 3 escudos y llevas ${game.getPlayer2Succes()} </p>
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
                        <button  class="buttom" type="submit" href="/onlineGame/play/${game.id}">Enviar</button>
                    </div>
                    </form:form>

                    <a class="buttom-danger" href ="/onlineGame/leave/${game.id}">Abandonar</a>

                </div>

                <script src="/js/autocomplete.js"></script>
        </div>
    </div>
</CUPES:layout>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="game">
    <div class="doBody">
        <div class="theBody">
            <h1 class="game">¿Es suficieste este pedazo para que adivines el escudo?</h1>
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
            <div>
                <div class="column">
                    <div class="imageContainer">
                        <div class="gameImage">
                            <img class="game">
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="imageContainer" style="position: relative;">
                        <img src="${imageUrl}" style="width: 500px;height: 500px;border-color: black;box-shadow: 0 0 10px rgba(0, 0, 0);background-color: white;margin-left: 125px;">
                        ${fullImageStyle}
                       
                    </div>
                </div>
            </div>

            <p class="game">Es el turno de <c:out value="${game.getActualPlayer()}"/></p>
            
            <form:form modelAttribute="logo">
                <div class="logoSelector">
                    <p class="game"><c:out value="${shiftsMessage}"/></p>
                    <div>
                        <form:label path="name">¿De quién es este escudo?</form:label>
                    </div>
                    <input class="input-game" name="name"  path="name" type="text" id="autocompleteInput" list="logos" autocomplete="off">
                    <datalist id="logos"></datalist>
                
                    <div>
                        <button  class="buttom" type="submit" href="/localGame/play/${game.id}?token=${game.token}">Enviar</button>
                    </div>
                </div>
            </form:form>

            <script src="/js/autocomplete.js"></script>
        </div>
    </div>
</CUPES:layout>
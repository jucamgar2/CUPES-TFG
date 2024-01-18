<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Partida Local">
        <div class="block">
            <h1 class="game">¿Es suficiente este pedazo para que adivines el escudo?</h1>
            <c:if test="${not empty errors}">
                <div id="error-notifications">
                    <c:forEach items="${errors}" var="error">
                        <div class="error-notification">
                            <span><c:out value="${error}"/></span>
                            <button class="close-button">×</button>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${succes}">
                <div id="succes">
                    <div class="succes-notification">
                        <span>¡Enhorabuena! El jugador 1 ha acertado</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>
            <div>
                <div class="center-items">
                    <div class="image-container" style="position: relative;">
                        <img src=<c:out value="${imageUrl}"/> style="width: 500px;height: 500px;border-color: black;box-shadow: 0 0 10px rgba(0, 0, 0);background-color: white;">
                        ${fullImageStyle}
                       
                    </div>
                </div>
            </div>

            <p class="game">Es el turno de <c:out value="${game.getActualPlayer()}"/></p>
            
            <form:form modelAttribute="logo">
                <div class="logo-selector">
                    <p class="game"><c:out value="${shiftsMessage}"/></p>
                    <div>
                        <form:label path="name">¿De quién es este escudo?</form:label>
                    </div>
                    <input class="input-game" name="name"  path="name" type="text" id="autocompleteInput" list="logos" autocomplete="off">
                    <datalist id="logos"></datalist>
                
                    <div>
                        <button  class="buttom" type="submit" href="/localGame/play/<c:out value='${game.id}'/>?token=<c:out value='${game.token}'/>">Enviar</button>
                    </div>
                </div>
            </form:form>

            <script src="/js/autocomplete.js"></script>
            <script src="/js/error_script.js"></script>
        </div>
</CUPES:layout>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
        <div class="theBody">
            <h1>Estos son vuestros logos completos:</h1>

            <p class="game"><c:out value="${winnerMsg}"/></p>
            <p class="game"><c:out value="${shiftsP1}"/></p>
            <p class="game"><c:out value="${shiftsP2}"/></p>

            <div>
                <div class="column">
                    <p class="game">Este es el escudo de <c:out value="${game.player1Name}"/></p>
                    <div class="gameResult">
                        <img class="gameRes" src="${imageUrl1}" alt="Estamos teniendo problemas">  
                    </div>
                </div>
                <div class="column">
                    <p class="game">Este es el escudo de <c:out value="${game.player2Name}"/></p>
                    <div class="gameResult">
                        <img class="gameRes" src="${imageUrl2}" alt="Estamos teniendo problemas">  
                    </div>
                </div>
            </div>
            

            <c:out value="${message}"/>

        </div>
    </div>
</CUPES:layout>
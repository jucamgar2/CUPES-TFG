<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class="block">
            <h1 class="title"><c:out value="${winnerMsg}"/></h1>
            <p class="game"><c:out value="${shiftsP1}"/></p>
            <p class="game"><c:out value="${shiftsP2}"/></p>

            <div class="pc-view">
                <div class="column">
                    <p class="game">Este es el escudo de <c:out value="${game.player1Name}"/></p>
                    <div class="center-items">
                        <img class="welcome-logo" src=<c:out value="${imageUrl1}"/> alt="Estamos teniendo problemas">  
                    </div>
                </div>
                <div class="column">
                    <p class="game">Este es el escudo de <c:out value="${game.player2Name}"/></p>
                    <div class="center-items">
                        <img class="welcome-logo" src=<c:out value="${imageUrl2}"/> alt="Estamos teniendo problemas">  
                    </div>
                </div>
            </div>

            <div class="mobile-view">
                <div>
                    <p class="game">Este es el escudo de <c:out value="${game.player1Name}"/></p>
                    <div class="center-items">
                        <img class="welcome-logo" src=<c:out value="${imageUrl1}"/> alt="Estamos teniendo problemas">  
                    </div>
                </div>
                <div>
                    <p class="game">Este es el escudo de <c:out value="${game.player2Name}"/></p>
                    <div class="center-items">
                        <img class="welcome-logo" src=<c:out value="${imageUrl2}"/> alt="Estamos teniendo problemas">  
                    </div>
                </div>
            </div>
            

            <a class="buttom" href="/game/localGame/new">Nueva partida</a>

    </div>
</CUPES:layout>
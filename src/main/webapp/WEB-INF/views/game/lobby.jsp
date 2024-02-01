<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Unirse a una partida onine">
    <div class="block">
            <h1 class="title">Cuando ambos jugadores estéis listos empezará la partida</h1>
            <div>
                <div class="column">
                    <div>
                        <p class="game">Jugador 1:</p>
                    </div>
                    <div>
                        <p class="game"><c:out value="${game.getPlayer1().getUsername()}"/></p>
                    </div>
                    <p class="game">Estado: 
                        <c:if test="${game.getPlayer1IsReady()}"> 
                            <p class="game ready">Estoy Listo</p>
                        </c:if>
                        <c:if test="${not game.getPlayer1IsReady()}"> 
                            <p class="game not-ready">No estoy Listo</p>
                        </c:if>
                    </p>
                </div>
                <div class="column">
                    <div>
                        <p class="game">Jugador 2:</p>
                    </div>
                    <div>
                        <p class="game"><c:out value="${game.getPlayer2().getUsername()}"/></p>
                    </div>
                    <div>
                        <p class="game">Estado: 
                            <c:if test="${game.getPlayer2IsReady()}"> 
                                <p class="game ready">Estoy Listo</p>
                            </c:if>
                            <c:if test="${not game.getPlayer2IsReady()}"> 
                                <p class="game not-ready">No estoy Listo</p>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
            <div class="block">
                <a class="buttom" href="/game/onlineGame/start/<c:out value='${game.getId()}'/>">Estoy Listo</a>
                <a class="buttom-danger" href="/game/onlineGame/leave/<c:out value='${game.getId()}'/>">Salir</a>
            </div>
            <c:if test="${game.getPlayer2()==null}">
                <div class="local-game-message">
                    <p class ="local-game-message">
                        Si tarda mucho en unirse un jugador, puede que el flujo de jugadores que se quieren unir a una partida sea muy pequeño comparado a la cantidad de jugadores que crean partidas, podrías probar a unirte a una partida o jugar en los modos de juego para un jugador
                    </p>
                </div>
            </c:if>
        <script src="/js/start_game.js"></script>
    </div>
</CUPES:layout>
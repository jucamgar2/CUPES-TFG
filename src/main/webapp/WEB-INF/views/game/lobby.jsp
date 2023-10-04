<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Unirse a una partida onine">
    <div class="doBody">
        <div class="theBody">
            <p>Jugador 1:</p><c:out value="${game.getPlayer1().getUsername()}"/>
            <p>Jugador 2:</p><c:out value="${game.getPlayer2().getUsername()}"/>
            <a href="/game/onlineGame/start/${game.getId()}">Estoy Listo</a>
            <a href="/game/onlineGame/leave/${game.getId()}">Salir</a>
        </div>
    </div>
</CUPES:layout>
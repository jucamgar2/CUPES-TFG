<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>

<CUPES:layout title="Seleccionar modo">
    <div class="doBody">
        <h1>Selecciona el modo de juego al que quieras jugar</h1>
        <h2>Recuerda que si no has iniciado sesión, solo estaran disponibles el modo un juegador con temática de banderas y de escudos de equipos de fútbol</h2>

        <div>
            <p>Temática de futbol</p>
            <a href="/game/new">Un jugador</a>
            <a href="/localGame/new">Uno contra uno local</a>
        </div>
    </div>
</CUPES:layout>
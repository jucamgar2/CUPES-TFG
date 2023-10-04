<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>

<CUPES:layout title="Seleccionar modo">
    <div class="doBody">
        <div class="theBody">
            <h1>Selecciona el modo de juego al que quieras jugar</h1>
            <div>
                <a class="buttom" href="/game/new">Un jugador</a>
                <a class="buttom" href="/game/localGame/new">Uno contra uno local</a>
                <a class="buttom" href="/game/onlineGame/new">Crear una partida uno contra uno en linea</a>
                <a class="buttom" href="/game/onlineGame/join">Unirse a una partida uno contra uno en linea</a>
            </div>
        </div>
    </div>
</CUPES:layout>
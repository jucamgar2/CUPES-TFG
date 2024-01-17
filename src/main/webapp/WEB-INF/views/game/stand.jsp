<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Unirse a una partida onine">
    <div class = "block"> 
            <h1 class="title">Terminaste, espera a que tu rival termine </h1>
            <p class="game">Mientras que esperas a que tu rival termine, te proponemos este minijuego donde deberás levantar las cartas de dos en dos para encontrar las parejas</p>  
            <div class="grid-container">

            </div>
                <p class="game">Nº de intentos: <span class="shifts"></span></p>
            <div class="center-items">
                <button class="buttom" onclick="restart()">Reiniciar</button>
            </div>
            <script src="/js/memory_game.js"></script>
        <script src="/js/finish_game.js"></script>
    </div>
</CUPES:layout>
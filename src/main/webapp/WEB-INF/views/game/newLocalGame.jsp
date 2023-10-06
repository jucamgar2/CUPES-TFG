<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <div class="new-local-game">
            <h1>Para jugar al modo uno contra uno de forma local, primero debes introducir un apodo para cada jugador</h1>
                <form:form modelAttribute="localGame">
                    <div>
                        <div class="column">
                            <div>
                                <form:label path="player1Name">Nombre del jugador 1</form:label>
                            </div>
                            <div>
                                <input class = "input-large" name="player1Name"  path="player1Name" type="text" >
                            </div>
                        </div>
                        <div class="column">
                            <div>
                                <form:label path="player2Name">Nombre del jugador 2</form:label>
                            </div>
                            <div>
                                <input class = "input-large" name="player2Name"  path="player2Name" type="text" >
                            </div>
                        </div>
                    </div>
                    
                    <div class="local-game-start">
                        <div >
                            <button class ="buttom"type="submit" href="/localGame/new">Comenzar</button>
                        </div>
                    </div>
                    <div class="local-game-message">
                        <p class ="local-game-message">¡Importante! Recordad que una vez que envieis este formulario para registrar el nombre de los jugadores, comenzará la partida y empezará a contar el tiempo para el jugador número 1</p>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</CUPES:layout>
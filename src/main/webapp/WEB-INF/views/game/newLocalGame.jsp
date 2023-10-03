<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1>Para jugar al modo uno contra uno de forma local, primero debes introducir un apodo para cada jugador</h1>
            <h2>Recuerda que una vez escribais vuestros nombre el jeugo empezara el tiempo del jugador número 1 empezará a contar y el tiempo que tarde cada uno puede ser determinante</h2>
            <form:form modelAttribute="localGame">
                <div>
                    <form:label path="player1Name">Nombre del jugador 1</form:label>
                    <input name="player1Name"  path="player1Name" type="text" >

                    <form:label path="player2Name">Nombre del jugador 2</form:label>
                    <input name="player2Name"  path="player2Name" type="text" >
                </div>
                <div>
                    <button type="submit" href="/localGame/new">Comenzar</button>
                </div>
            </form:form>
        </div>
    </div>
</CUPES:layout>
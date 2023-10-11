<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
        <div class = "theBody">
            <div class="game-result">
                <h1><c:out value="${message2}"/></h1>
                <p class="game">Este es el logo completo:</p>

                <div class="gameResult">
                    <img class="gameRes" src="${imageUrl}" alt="Estamos teniendo problemas">  
                </div>
                <p class="game" ><c:out value="${message}"/></p>
            </div>
            <a href="/game/new" class="buttom">Nueva partida</a>
        </div>
    </div>
</CUPES:layout>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
        <div class = "theBody">
            <h1 class="game">El ganador ha sido: <c:out value="${game.getWinner()}"/></h1>
            <p class="game">Vuestras estadísticas han diso las siguientes:</p>
            <p class="game"> <c:out value="${game.getPlayer1().getUsername()}"/> -> Intentos: <c:out value="${game.getPlayer1Shifts()}"/> , Timepo: <c:out value="${player1Time}"/> segundos</p>
            <p class="game"> <c:out value="${game.getPlayer2().getUsername()}"/> -> Intentos: <c:out value="${game.getPlayer2Shifts()}"/> , Timepo: <c:out value="${player2Time}"/> segundos</p>
            <div>
                <div>
                    <div class="column">
                        <p class="game">Escudos de <c:out value="${game.getPlayer1().getUsername()}"/>:</p>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl1}" alt="Estamos teniendo problemas">  
                                <img class="game-res-online" src="${imageUrl2}" alt="Estamos teniendo problemas">  
                            </div>
                        </div>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl3}" alt="Estamos teniendo problemas">  
                            </div>
                        </div>
                    </div>
                    <div class="column">
                        <p class="game">Escudos de <c:out value="${game.getPlayer2().getUsername()}"/>:</p>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl4}" alt="Estamos teniendo problemas">  
                                <img class="game-res-online" src="${imageUrl5}" alt="Estamos teniendo problemas">  
                            </div>
                        </div>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl6}" alt="Estamos teniendo problemas">  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</CUPES:layout>
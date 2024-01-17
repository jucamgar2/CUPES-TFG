<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class="block">
            <c:if test="${game.getPlayer1Leaves() || game.getPlayer2Leaves()}">
                <p class = "game"> <c:out value ="${leavemsg}"/></p>
            </c:if>
            <c:if test="${ !game.getPlayer1Leaves() && !game.getPlayer2Leaves()}">
                <h1 class="title">El ganador ha sido <c:out value="${game.getWinner()}"/></h1>
                <p class="game">Vuestras estadísticas han sido las siguientes:</p>
                <p class="game"> <c:out value="${game.getPlayer1().getUsername()}"/> -> Intentos: <c:out value="${game.getPlayer1Shifts()}"/> , Tiempo: <c:out value="${player1Time}"/> segundos, Aciertos: <c:out value="${game.getPlayer1Succes()}"/></p>
                <p class="game"> <c:out value="${game.getPlayer2().getUsername()}"/> -> Intentos: <c:out value="${game.getPlayer2Shifts()}"/> , Tiempo: <c:out value="${player2Time}"/> segundos, Aciertos: <c:out value="${game.getPlayer2Succes()}"/></p>
            </c:if>
            <div class="pc-view">
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
                <div>
                    <a class="buttom" href="/game/onlineGame/new">Crear una partida 1vs1 en linea</a>
                </div>
                <a class="buttom" href="/game/onlineGame/join">Unirse a una partida 1vs1 en linea</a>
            </div>
            <div class="mobile-view">
                <div>
                        <p class="game">Escudos de <c:out value="${game.getPlayer1().getUsername()}"/>:</p>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl1}" alt="Estamos teniendo problemas">  
                                <img class="game-res-online" src="${imageUrl2}" alt="Estamos teniendo problemas">
                                <img class="game-res-online" src="${imageUrl3}" alt="Estamos teniendo problemas">   
                            </div>
                        </div>


                        <p class="game">Escudos de <c:out value="${game.getPlayer2().getUsername()}"/>:</p>
                        <div>
                            <div class="gameResult">
                                <img class="game-res-online" src="${imageUrl4}" alt="Estamos teniendo problemas">  
                                <img class="game-res-online" src="${imageUrl5}" alt="Estamos teniendo problemas"> 
                                <img class="game-res-online" src="${imageUrl6}" alt="Estamos teniendo problemas">   
                            </div>

                </div>
                <div class="block">
                    <a class="buttom" href="/game/onlineGame/new">Crear una partida 1vs1 en línea</a>
                    <a class="buttom" href="/game/onlineGame/join">Unirse a una partida 1vs1 en línea</a>
                </div>
                
            </div>
    </div>
</CUPES:layout>
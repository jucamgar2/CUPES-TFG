<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="statistics">
    <div class="doBody">
        <div class="theBody">
            <div class="statistics-container">
                <div class="general-statistics">
                    <h2>Estadísticas generales del modo un jugador</h2>
                    <table class="statistics-table">
                        <tr>
                            <td>Número de partidas:</td>
                            <td><c:out value="${totalGames}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias:</td>
                            <td><c:out value="${totalWins}" /></td>
                        </tr>
                        <tr>
                            <td>Porcentaje de victorias:</td>
                            <td><c:out value="${totalWinRate}" />%</td>
                        </tr>
                        <tr>
                            <td>Victorias con un intento:</td>
                            <td><c:out value="${totalWinsWithOneShift}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con dos intentos:</td>
                            <td><c:out value="${totalWinsWithTwoShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con tres intentos:</td>
                            <td><c:out value="${totalWinsWithThreeShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con cuatro intentos:</td>
                            <td><c:out value="${totalWinsWithFourShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar:</td>
                            <td><c:out value="${totalAverageShiftsToWin}" /></td>
                        </tr>
                    </table>
                </div>
                <sec:authorize access="isAuthenticated()">
                    <div class="user-statistics">
                        <h2>Estadísticas del usuario para el modo un jugador</h2>
                        <table class="statistics-table">
                            <tr>
                                <td>Número de partidas:</td>
                                <td><c:out value="${userGames}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias:</td>
                                <td><c:out value="${userWins1}" /></td>
                            </tr>
                            <tr>
                                <td>Porcentaje de victorias:</td>
                                <td><c:out value="${userWinRate}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en un intento:</td>
                                <td><c:out value="${userWinsWithOneShift}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en dos intentos:</td>
                                <td><c:out value="${userWinsWithTwoShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en tres intentos:</td>
                                <td><c:out value="${userWinsWithThreeShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en cuatro intentos:</td>
                                <td><c:out value="${userWinsWithFourShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Media de intentos para ganar:</td>
                                <td><c:out value="${userAverageShiftsToWin}" /></td>
                            </tr>
                        </table>
                    </div>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <div>
                        <h1>Debes iniciar sesión si quieres poder ver tus Estadísticas</h1>
                    </div>
                </sec:authorize>
                
            </div>
            <div class="statistics-container">
                <div class="general-statistics">
                    <h2>Estadísticas de juegos en línea</h2>
                    <table class="statistics-table">
                        <tr>
                            <td>Número de partidas en línea:</td>
                            <td><c:out value="${totalOnlineGames}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias del creador de la partida:</td>
                            <td><c:out value="${totalOnlineWinsByPlayer1}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias del jugador que se une a la partida:</td>
                            <td><c:out value="${totalOnlineWinsByPlayer2}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias perfectas(en el primer intento):</td>
                            <td><c:out value="${totalOnlinePerfectWins}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar del creador de la partida:</td>
                            <td><c:out value="${totalOnlineAverageOfShiftsToWinByPlayer1}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar del jugador que se une a la partida:</td>
                            <td><c:out value="${totalOnlineAverageOfShiftsToWinByPlayer2}" /></td>
                        </tr>
                    </table>
                </div>
                <div class="user-statistics">
                    <sec:authorize access="isAuthenticated()">
                        <h2>Estadísticas de juegos en línea del usuario</h2>
                        <table class="statistics-table">
                            <tr>
                                <td>Número de juegos en línea:</td>
                                <td><c:out value="${userOnlineGames}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias:</td>
                                <td><c:out value="${userWins}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias perfectas:</td>
                                <td><c:out value="${userPerfectWins}" /></td>
                            </tr>
                            <tr>
                                <td>Media de intentos para ganar :</td>
                                <td><c:out value="${userAverageOfShiftsToWinByPlayer1}" /></td>
                            </tr>
                            <tr>
                                <td>Tasa de victorias del usuario:</td>
                                <td><c:out value="${userWinRate}" /></td>
                            </tr>
                            <tr>
                                <td>Tasa de victorias perfectas del usuario:</td>
                                <td><c:out value="${perfectWinRate}" /></td>
                            </tr>
                        </table>
                    </sec:authorize>
                </div>
            </div>
            <div>
                <a href="/statistics/GA/ranking" class="buttom">Ver el ranking de jugadores en solitario</a>
                <a href="/statistics/GO/ranking" class="buttom">Ver el ranking de jugadores en línea</a>
            </div>
        </div>
    </div>
</CUPES:layout>
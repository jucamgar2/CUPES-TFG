<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="statistics">
    <div class="block">
        <sec:authorize access="!isAuthenticated()">
                    <div>
                        <h1 class="title">Debes iniciar sesión si quieres poder ver tus Estadísticas</h1>
                    </div>
                </sec:authorize>
            <div class="statistics-container">
                <div class="general-statistics">
                    <table class="statistics-table">
                        <thead>
                            <tr>
                                <th colspan="2">
                                    Estadísticas generales del modo un jugador
                                </th>
                            </tr>
                        </thead>
                        <tr>
                            <td>Número de partidas:</td>
                            <td class="left"><c:out value="${totalGames}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias:</td>
                            <td class="left"><c:out value="${totalWins}" /></td>
                        </tr>
                        <tr>
                            <td>Porcentaje de victorias:</td>
                            <td class="left"><c:out value="${totalWinRate}" />%</td>
                        </tr>
                        <tr>
                            <td>Victorias con un intento:</td>
                            <td class="left"><c:out value="${totalWinsWithOneShift}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con dos intentos:</td>
                            <td class="left"><c:out value="${totalWinsWithTwoShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con tres intentos:</td>
                            <td class="left"><c:out value="${totalWinsWithThreeShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Victorias con cuatro intentos:</td>
                            <td class="left"><c:out value="${totalWinsWithFourShifts}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar:</td>
                            <td class="left"><c:out value="${totalAverageShiftsToWin}" /></td>
                        </tr>
                    </table>
                </div>
                
                    <div class="user-statistics">
                        <table class="statistics-table">
                            <thead>
                                <tr>
                                    <th colspan="2">Estadísticas del usuario para el modo un jugador</th>
                                </tr>
                            </thead>
                            <tr>
                                <td>Número de partidas:</td>
                                <td class="left"><c:out value="${userGames}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias:</td>
                                <td class="left"><c:out value="${userWins1}" /></td>
                            </tr>
                            <tr>
                                <td>Porcentaje de victorias:</td>
                                <td class="left"><c:out value="${uwr}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en un intento:</td>
                                <td class="left"><c:out value="${userWinsWithOneShift}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en dos intentos:</td>
                                <td class="left"><c:out value="${userWinsWithTwoShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en tres intentos:</td>
                                <td class="left"><c:out value="${userWinsWithThreeShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Victorias en cuatro intentos:</td>
                                <td class="left"><c:out value="${userWinsWithFourShifts}" /></td>
                            </tr>
                            <tr>
                                <td>Media de intentos para ganar:</td>
                                <td class="left"><c:out value="${userAverageShiftsToWin}" /></td>
                            </tr>
                        </table>
                    </div>
                
                
            </div>
            <div class="statistics-container">
                <div class="general-statistics">
                    <table class="statistics-table">
                        <thead>
                            <tr>
                                <th colspan="2">Estadísticas de partidas en línea</th>
                            </tr>
                        </thead>
                        <tr>
                            <td>Número de partidas en línea:</td>
                            <td class="left"><c:out value="${totalOnlineGames}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias del creador de la partida:</td>
                            <td class="left"><c:out value="${totalOnlineWinsByPlayer1}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias del jugador que se une a la partida:</td>
                            <td class="left"><c:out value="${totalOnlineWinsByPlayer2}" /></td>
                        </tr>
                        <tr>
                            <td>Número de victorias perfectas(en el primer intento):</td>
                            <td class="left"><c:out value="${totalOnlinePerfectWins}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar del creador de la partida:</td>
                            <td class="left"><c:out value="${totalOnlineAverageOfShiftsToWinByPlayer1}" /></td>
                        </tr>
                        <tr>
                            <td>Media de intentos para ganar del jugador que se une a la partida:</td>
                            <td class="left"><c:out value="${totalOnlineAverageOfShiftsToWinByPlayer2}" /></td>
                        </tr>
                    </table>
                </div>
                <div class="user-statistics">
                        <table class="statistics-table">
                            <thead>
                                <tr>
                                    <th colspan="2">Estadísticas de partidas en línea del usuario</th>
                                </tr>
                            </thead>
                            <tr>
                                <td>Número de partidas:</td>
                                <td class="left"><c:out value="${userOnlineGames}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias:</td>
                                <td class="left"><c:out value="${userWins}" /></td>
                            </tr>
                            <tr>
                                <td>Número de victorias perfectas:</td>
                                <td class="left"><c:out value="${userPerfectWins}" /></td>
                            </tr>
                            <tr>
                                <td>Media de intentos para ganar :</td>
                                <td class="left"><c:out value="${userAverageOfShiftsToWinByPlayer1}" /></td>
                            </tr>
                            <tr>
                                <td>Tasa de victorias del usuario:</td>
                                <td class="left"><c:out value="${userWinRate}" /></td>
                            </tr>
                            <tr>
                                <td>Tasa de victorias perfectas del usuario:</td>
                                <td class="left"><c:out value="${perfectWinRate}" /></td>
                            </tr>
                        </table>
                </div>
            </div>
            
            <div>
                <a href="/statistics/GA/ranking" class="buttom">Ver el ranking de jugadores en solitario</a>
                <a href="/statistics/GO/ranking" class="buttom">Ver el ranking de jugadores en línea</a>
            </div>
    </div>
</CUPES:layout>
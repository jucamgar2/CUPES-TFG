<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Perfil">
    <div class="doBody">
        <div class="theBody">
            <p class="game">Perfil de <c:out value="${player.getUsername()}"/></p>
            <p class="game">Nombre: <c:out value="${player.getName()}"/></p>
            <p class="game">Email: <c:out value="${player.getMail()}"/></p>
            <p class="game">Fecha de nacimiento: <c:out value="${player.getBirthDate()}"/></p>
            <c:if test="${player.getUsername()==principal.getName()}">
                <a href="/players/edit" class="buttom">Editar mi perfil</a>
            </c:if>
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
            <div>
                <sec:authorize access="hasAuthority('admin')">
                    <a href="/administration/players/exportData/${player.getUsername()}" class="buttom">Exportar datos del jugador</a>
                </sec:authorize>
            </div>
        </div>
    </div>
</CUPES:layout>
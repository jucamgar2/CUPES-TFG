<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="statistics">
    <div class="doBody">
        <div class="theBody">
            <h1><c:out value = "${msg}"/></h1>
            <h2>Ranking de Partidas</h2>
            <table>
                <tr>
                    <th>Jugador</th>
                    <th>Número de Juegos</th>
                </tr>
                <c:forEach var="entry" items="${rankingGame}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>

            <h2>Ranking de Victorias</h2>
            <table >
                <tr>
                    <th>Jugador</th>
                    <th>Número de Victorias</th>
                </tr>
                <c:forEach var="entry" items="${rankingWin}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</CUPES:layout>
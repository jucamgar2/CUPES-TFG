<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Ranking">
    <div class="block">
            <h1 class="title"><c:out value = "${msg}"/></h1>
            <h2>Ranking de Partidas</h2>
            <div class="center-items">
                <table class="statistics-table">
                    <thead>
                        <tr>
                            <th>Jugador</th>
                            <th class="left">Número de partidas</th>
                        </tr>
                    </thead>
                    <c:forEach var="entry" items="${rankingGame}">
                        <tr>
                            <td>${entry.key}</td>
                            <td class="left">${entry.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <h2>Ranking de Victorias</h2>
            <div class="center-items">
                <table class="statistics-table">
                    <thead>
                        <tr>
                            <th>Jugador</th>
                            <th class="left">Número de victorias</th>
                        </tr>
                    </thead>
                    <c:forEach var="entry" items="${rankingWin}">
                        <tr>
                            <td>${entry.key}</td>
                            <td class="left">${entry.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </div>
</CUPES:layout>
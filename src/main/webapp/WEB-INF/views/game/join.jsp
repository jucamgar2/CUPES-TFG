<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Unirse a una partida onine">
    <div>
        <c:forEach items="${games}" var="game">
            <a href="/onlineGame/join/${game.id}">Unirse a la partida ${game.id}</a>
        </c:forEach>
    </div>
</CUPES:layout>
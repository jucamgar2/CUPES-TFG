<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
        <div class = "theBody">
            <h2>Ganador: <c:out value="${game.getWinner()}"/></h2>
        </div>
    </div>
</CUPES:layout>
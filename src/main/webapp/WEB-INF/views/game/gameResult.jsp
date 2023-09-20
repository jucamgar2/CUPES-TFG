<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
    <p>${message2}</p>
    <h1>Este es el logo completo:</h1>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl}" alt="Estamos teniendo problemas">  
    </div>

    <p>${message}</p>
</div>
</CUPES:layout>
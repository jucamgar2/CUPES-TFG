<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
    <p>${winnerMsg}</p>
    <p>${shiftsP1}</p>
    <p>${shiftsP2}</p>
    <h1>Estos son vuestros logos completos:</h1>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl1}" alt="Estamos teniendo problemas">  
    </div>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl2}" alt="Estamos teniendo problemas">  
    </div>

    <p>${message}</p>
</div>
</CUPES:layout>
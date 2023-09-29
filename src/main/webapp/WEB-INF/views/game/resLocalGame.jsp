<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
    
    <h1>Estos son vuestros logos completos:</h1>

    <h1><c:out value="${winnerMsg}"/></h1>
    <h1><c:out value="${shiftsP1}"/></h1>
    <h1><c:out value="${shiftsP2}"/></h1>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl1}" alt="Estamos teniendo problemas">  
    </div>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl2}" alt="Estamos teniendo problemas">  
    </div>

    <c:out value="${message}"/>
</div>
</CUPES:layout>
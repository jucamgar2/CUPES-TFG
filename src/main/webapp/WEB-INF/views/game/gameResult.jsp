<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
    
    <c:out value="${message2}"/>
    <h1>Este es el logo completo:</h1>

    <div class="gameResult">
        <img class="gameRes" src="${imageUrl}" alt="Estamos teniendo problemas">  
    </div>
    <c:out value="${message}"/>
</div>
</CUPES:layout>
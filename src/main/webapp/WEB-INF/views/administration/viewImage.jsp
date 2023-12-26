<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Resultado de la partida">
    <div class = "doBody">
        <div class = "theBody">
            <div class="game-result">
                <div class="gameResult">
                    <div class="view-image">
                        <!-- Líneas horizontales -->
                        <div class="grid-line horizontal-line" style="top: 25%;"></div>
                        <div class="grid-line horizontal-line" style="top: 50%;"></div>
                        <div class="grid-line horizontal-line" style="top: 75%;"></div>
                
                        <!-- Líneas verticales -->
                        <div class="grid-line vertical-line" style="left: 25%;"></div>
                        <div class="grid-line vertical-line" style="left: 50%;"></div>
                        <div class="grid-line vertical-line" style="left: 75%;"></div>
                
                        <!-- Imagen -->
                        <img class="gameRes" src="${image}" alt="Estamos teniendo problemas" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                </div>
                <div>
                    <c:forEach var="position" items="${positions}">
                        <div>
                            <p class="game" ><c:out value="${position.key}"/> :</p>
                            <p class="game"><c:out value="${position.value}"/></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
        </div>
    </div>
</CUPES:layout>
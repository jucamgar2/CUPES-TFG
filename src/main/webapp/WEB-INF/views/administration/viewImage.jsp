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
                        <div class="grid-line horizontal-line" style="top: 25%;"></div>
                        <div class="grid-line horizontal-line" style="top: 50%;"></div>
                        <div class="grid-line horizontal-line" style="top: 75%;"></div>
                
                        <div class="grid-line vertical-line" style="left: 25%;"></div>
                        <div class="grid-line vertical-line" style="left: 50%;"></div>
                        <div class="grid-line vertical-line" style="left: 75%;"></div>
                
                        <img class="gameRes" src="${image}" alt="Estamos teniendo problemas" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                </div>
                <p class="game">Nombre: <c:out value="${img.name}"/></p>
                <p class="game">País: <c:out value="${img.country}"/></p>
                <p class="game">¿Esta habilitada? 
                    <c:if test="${img.enabled}"> Sí</c:if>
                    <c:if test="${!img.enabled}"> No</c:if>
                </p>
                <p class="game">Número de partidas en las que ha aparecido: <c:out value="${games}"/></p>
                <p class="game">Número de partidas en las que ha aparecido y ha sido acertado: <c:out value="${success}"/></p>
                <p class="game">Ratio de acierto: <c:out value="${successRate}"/></p>
                <p class="game">Género:
                    <c:if test="${img.genre=='M'}"> Masculino</c:if>
                    <c:if test="${img.genre=='F'}"> Femenino</c:if>
                </p>
                <p class="game">Categoría: 
                    <c:if test="${img.category == 1}"> Primera división</c:if>
                    <c:if test="${img.category == 2}"> Segunda división</c:if>
                </p>

                <p class="game">¿El escudo muestra el nombre del equipo?
                    <c:if test="${img.hasName}"> Sí</c:if>
                    <c:if test="${!img.hasName}"> No</c:if>
                </p>
                <p class="game">¿El escudo muestra las iniciales del equipo?
                    <c:if test="${img.hasInitials}"> Sí</c:if>
                    <c:if test="${!img.hasInitials}"> No</c:if>
                </p>
                <p class="game">¿El escudo muestra el año de fundación?
                    <c:if test="${img.hasYear}"> Sí</c:if>
                    <c:if test="${!img.hasYear}"> No</c:if>
                </p>
                <div>
                    <c:if test="${img.enabled}">
                        <a href="/administration/images/disable/${img.id}" class="buttom-danger">Deshabilitar</a>
                    </c:if>
                    <c:if test="${!img.enabled}">
                        <a href="/administration/images/enable/${img.id}" class="buttom-positive">Habilitar</a>
                    </c:if>
                </div>
                <div>
                    <a href="/administration/images/exportData/${img.id}" class="buttom">Exportar datos</a>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Bienvenido">
            <c:forEach items="${errors}" var="error">
                <div class="error-notification">
                    <span>${error}</span>
                    <button class="close-button">×</button>
                </div>
            </c:forEach>
            <h1 class="titulo-principal">CUPES</h1>
            <div class="center-items">
                <img src="/images/Logotipo.png" alt="Logo" class="welcome-logo">
            </div>
            <div class="welcome">
                <h2>4 oportunidades para adivinar el nombre de un equipo de fútbol</h2>
                <div>
                    <a href="/game/new" class="buttom">Jugar</a>
                    <a href="#" class="buttom">¿Como jugar?</a>
                    <a href="/players/new" class="buttom">Registrarse</a>
                </div>
            </div>
</CUPES:layout>
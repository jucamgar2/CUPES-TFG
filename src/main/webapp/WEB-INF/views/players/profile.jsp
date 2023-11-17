<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Perfil">
    <div class="doBody">
        <div class="theBody">
            <p class="game">Perfil de <c:out value="${player.getUsername()}"/></p>
            <p class="game">Nombre: <c:out value="${player.getName()}"/></p>
            <p class="game">Email: <c:out value="${player.getMail()}"/></p>
            <p class="game">Fecha de nacimiento: <c:out value="${player.getBirthDate()}"/></p>
            <a href="/players/edit" class="buttom">Editar mi perfil</a>
        </div>
    </div>
</CUPES:layout>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1>Registrarse</h1>
            <form:form modelAttribute="player">
                <label for="username">Usuario:</label>
                <input type="text" id="username" name="username" required>
                <div class="errors" style="color:red">
                    <c:out value="${existsError}"/>
                    <c:out value="${usernameError}"/>
                </div>
                <br>
                <label for="password">Contraseña:</label>
                <input type="text" id="password" name="password" required>
                <div class="errors" style="color:red">
                    <c:out value="${passwordError}"/>
                </div>
                <br>
                <input type="submit" value="Guardar">
            </form:form>
        </div>
    </div>
</CUPES:layout>
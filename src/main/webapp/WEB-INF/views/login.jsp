<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <div class="register">
                <h1 class="register">Iniciar Sesión</h1>
                <c:if test="${error}">
                    <p class="game">Lo sentimos pero el usuario y la contraseña no coinciden con los de ningun usuario registrado</p>
                </c:if>
                <form:form modelAttribute="player">
                    <div>
                        <label for="username">Usuario:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="username" name="username" required>
                    </div>
                    <div class="errors" style="color:red">
                        <c:out value="${existsError}"/>
                        <c:out value="${usernameError}"/>
                    </div>
                    <br>
                    <div>
                        <label for="password">Contraseña:</label>
                    </div>
                    <div>
                        <input type="text" class ="input-large" id="password" name="password" required>
                    </div>
                    <div class="errors" style="color:red">
                        <c:out value="${passwordError}"/>
                    </div>
                    <br>
                    <button class="buttom" type="submit">Iniciar Sesión</button>
                </form:form>
            </div>
        </div>
    </div>
</CUPES:layout>
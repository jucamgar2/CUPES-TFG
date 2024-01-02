<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<CUPES:layout title="Registrarse">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'dd/mm/yy' });
            });
        </script>

            <div class="register">
                <h1 class="title">Registrarse</h1>
                <c:if test="${not empty errors}">
                    <div id="error-notifications">
                        <c:forEach items="${errors}" var="error">
                            <div class="error-notification">
                                <span>${error}</span>
                                <button class="close-button">×</button>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <form:form modelAttribute="player">
                    <div>
                        <label for="username">Usuario:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="username" name="username" required>
                    </div>
                   
                    <div>
                        <label for="password">Contraseña:</label>
                        <i id="iconoMostrar" class="fas fa-eye"></i>
                    </div>
                    <div>
                        
                        <input type="password" class ="input-large" id="password" name="password" required>
                    </div>
                    <div class="errors" style="color:red">
                        <c:out value="${passwordError}"/>
                    </div>

                    <div>
                        <label for="name">Nombre:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="name" name="name" required>
                    </div>

                    <div>
                        <label for="mail">Email:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="mail" name="mail" required>
                    </div>

                    <div>
                        <label for="birthDate">Fecha de nacimiento:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="birthDate" name="birthDate" required>
                    </div>

                    <button class="buttom" type="submit">Registrarme</button>
                </form:form>
                <div class="registerInfo">
                    <h2>Registrate para poder tener acceso al modo de juego online, donde podrás competir contra jugadores de todo el mundo</h2>
                    <h2>Aunque si no quieres, puedes seguir jugando al modo un jugador para seguir practicando y adquiriendo más conocimiento</h2>
                    <a class="buttom" href="/game/new">Jugar</a>
                </div>
            </div>

        <script src="/js/error_script.js"></script>
        <script src="/js/show_pasword.js"></script>

</CUPES:layout>
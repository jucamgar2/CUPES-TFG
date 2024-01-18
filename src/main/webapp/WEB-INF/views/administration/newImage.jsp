<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<CUPES:layout title="Página de Inicio">
    <div class="block">
            <c:if test="${not empty errors}">
                <div id="error-notifications">
                    <c:forEach items="${errors}" var="error">
                        <div class="error-notification">
                            <span><c:out value="${error}"/></span>
                            <button class="close-button">×</button>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <form:form modelAttribute="imageForm"  enctype="multipart/form-data">
                <div>
                    <form:label path="file">Añade aquí la imagen</form:label> 
                    <input class="buttom" name="file"  path="file" type="file">
                </div>
                <div>
                    <form:label path="name">¿Cuál es el nombre completo de este equipo?</form:label>
                </div>
                <div>
                    <input class="input-game" name="name"  path="name" type="text">
                </div>
                <div>
                    <form:label path="country">¿Cuál es el país de este equipo?</form:label>
                </div>
                <div>
                    <input class="input-game" name="country"  path="country" type="text">
                </div>
                <div>
                    <label for="category">Selecciona la categoría del equipo: </label>
                    <select class="input-large" name="category" id="category">
                        <option value="1">Primera división</option>
                        <option value="2">Segunda división</option>
                    </select>
                </div>
                <div>
                    <label for="genre">Selecciona el género del equipo: </label>
                    <select class="input-large" name="genre" id="genre">
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>
                    </select>
                </div>
                
                <div>
                    <label for="hasName">¿El escudo muestra el nombre del equipo? </label>
                    <select class="input-large" name="hasName" id="hasName">
                        <option value="true">Si</option>
                        <option value="false">No</option>
                    </select>
                </div>

                <div>
                    <label for="hasInitials">¿El escudo muestra las iniciales del equipo? </label>
                    <select class="input-large" name="hasInitials" id="hasInitials">
                        <option value="true">Si</option>
                        <option value="false">No</option>
                    </select>
                </div>

                <div>
                    <label for="hasYear">¿El escudo muestra el año de fundación del equipo? </label>
                    <select class="input-large" name="hasYear" id="hasYear">
                        <option value="true">Si</option>
                        <option value="false">No</option>
                    </select>
                </div>

                
                <div>
                    <label for="enabled">¿Deseas habilitar el escudo para que aparezca en partidas? </label>
                    <select class="input-large" name="enabled" id="enabled">
                        <option value="true">Si</option>
                        <option value="false">No</option>
                    </select>
                </div>
                <div>
                    <button  class="buttom" type="submit" href="/administration/image/new">Enviar</button>
                </div>
            </form:form>
    </div>
    <script src="/js/error_script.js"></script>
</CUPES:layout>
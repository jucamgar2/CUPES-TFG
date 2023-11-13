<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <form:form modelAttribute="imageForm"  enctype="multipart/form-data">
                <div>
                    <form:label path="file">Añade aqui la imagen</form:label> 
                    <input name="file"  path="file" type="file">
                </div>
                <div>
                    <form:label path="name">¿Cual es el nombre completo de este equipo?</form:label>
                </div>
                <div>
                    <input class="input-game" name="name"  path="name" type="text">
                </div>
                <div>
                    <button  class="buttom" type="submit" href="/administration/image/new">Enviar</button>
                </div>
            </form:form>
        </div>
    </div>
</CUPES:layout>
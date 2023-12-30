<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1>Como administrador puedes hacer muchas cosas por ejemplo:</h1>
            <div>
                <p class="game">
                    <a class="buttom" href="/administration/players">Administrar jugadores</a>
                </p>
            </div>
            <div>
                <p class="game">
                    <a class="buttom" href="/administration/images">Administrar imágenes</a>
                </p>
            </div>
            <div>
                <p class="game">
                    <a class="buttom" href="/administration/images/new">Añadir imágenes</a>
                </p>
            </div>
            <div>
                <p class="game">
                    <a class="buttom" href="/administration/images/statistics">Ver estadísticas de las imágenes</a>
                </p>
            </div>
            
        </div>
    </div>
</CUPES:layout>
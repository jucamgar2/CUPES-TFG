<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1 class="tituloPrincipal">Bienvenido a</h1>
            <!-- 
            <h1>Bienvenido a Con Un Pedazo Es Suficiente, tu nuevo juego preferido</h1>
            <h3>En esta aplicación podrás demostrar tus conocimientos futbolísticos</h3>
            <h2>áéíóú@你好</h2>
            <p>Si es la primera vez que entras a CUPES, te recomendamos que te registres</p>
            <p>Aunque realmente no es necesario para probar nuestro modo de juego para un jugador</p>

            <p>Si eres un jugador experimentado puedes competir contra tus amigos desde el mismo dispositivo o contra cualquier persona del mundo en nuestro modo de juego multijugador en linea</p>
            -->
            <div class="animation">
                <div id="animated-text"></div>
            </div>
            <div class="welcome">
                <h2>CUPES, Con un Pedazo es Suficiente será tu nuevo juego de adivinanzas visuales preferido.</h2>
                <h2>En esta aplicación podrás demostrar tus conocimientos.</h2>
                <sec:authorize access="!isAuthenticated()">
                    <h2>Si es la primera vez que entras a CUPES, te recomendamos que te registres:</h2>
                    <a href="/players/new" class="buttom">Registrarse</a>
                    <h2>Aunque realmente no es necesario para probar nuestro modo de juego para un jugador:</h2>
                    <a href="/game/new" class="buttom">Probar modo para un jugador</a>
                    <h2>Otra opción si no quieres registrarte es que compitas con un amigo en el modo local:</h2>
                    <a href="/game/localGame/new" class="buttom">Probar 1vs1 local</a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <h2>Prueba desde ya nuestro modo competitivo en linea donde tienes la opción de crear una partida o unirte a una partida ya creada:</h2>
                    <a href="/game/onlineGame/new" class="buttom">Crear partida 1vs1 en linea</a>
                    <a href="/game/onlineGame/join" class="buttom">Unirse a una partida 1vs1 en linea</a>
                </sec:authorize>

            </div>
        </div>
    </div>
</CUPES:layout>
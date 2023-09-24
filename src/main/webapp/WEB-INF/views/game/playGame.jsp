<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<CUPES:layout title="game">
    <div class="cosas">
    <div class="gameImage">
        <img class="game">
    </div>
</div>

    <p>Tienes un total de 3 intentos y has gastado ${game.getShift()} </p>
    
        <form:form modelAttribute="logo">
            <div class="logoSelector">
            <form:label path="name">¿De quién es este escudo?</form:label>

                <input name="name"  path="name" type="text" id="autocompleteInput" list="logos" autocomplete="off">
                <datalist id="logos"></datalist>
            </div>
        <div>
            <button  class="selectImage" type="submit" href="/game/play/${game.id}">Enviar</button>
        </div>
        </form:form>


        <script src="/js/autocomplete.js"></script>
        
</CUPES:layout>
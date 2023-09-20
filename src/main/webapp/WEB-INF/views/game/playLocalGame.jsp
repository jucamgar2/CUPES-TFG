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

    <h1>Es el turno de ${game.getActualPlayer()}</h1>

    <p>Intenta adivinar el nombre de este equipo en el menor número posible de intentos y lo más rapido posible </p>
    
        <form:form modelAttribute="logo">
            <div class="logoSelector">
            <form:label path="name">¿De quién es este escudo?</form:label>

                <input name="name"  path="name" type="text" id="autocompleteInput" list="logos" autocomplete="off">
                <datalist id="logos"></datalist>
            </div>
        <div>
            <button  class="selectImage" type="submit" href="/localGame/play/${game.id}">Enviar</button>
        </div>
        </form:form>


        <script>
            var input = document.getElementById("autocompleteInput");
            var datalist = document.getElementById("logos");
            fetch("/autocomplete/logos")
                .then(response => response.json())
                .then(data => {
                    data.forEach(function(word) {
                        var option = document.createElement("option");
                        option.value = word;
                        datalist.appendChild(option);
                    });
                });
            input.addEventListener("input", function() {
                var inputValue = input.value.toLowerCase();
                var options = datalist.getElementsByTagName("option");
                for (var i = 0; i < options.length; i++) {
                    var option = options[i];
                    if (option.value.toLowerCase().startsWith(inputValue)) {
                        option.hidden = false;
                    } else {
                        option.hidden = true;
                    }
                }
            });
        </script>
        
</CUPES:layout>
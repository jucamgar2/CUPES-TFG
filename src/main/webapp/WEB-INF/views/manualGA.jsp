<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Manual">

    <div class="block">
        <div class="manual">
            <h1>Manual de partidas en solitario</h1>
            Para jugar al modo de juego un jugador, no será necesario haber iniciado sesión ni haberse registrado, puesto que es un modo de juego rápido y cuya finalidad es practicar, aunque, si hemos iniciado sesión se guardarán nuestras estadísticas en este modo de juego. Una vez que en el selector de modos juegos pulsemos sobre jugar modo un jugador, la partida comenzará automáticamente y se nos mostrará la siguiente pantalla:
            <div class="pc-view">
                <img class="manual" src="/images/manual/8.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/8.jpeg"/>
            </div>
            Como se puede observar, se muestra un escudo de un equipo completamente cubierto a excepción de un solo pedazo, que sería la pista de este intento , además, hay un mensaje que nos dice cuántos intentos tenemos y cuantos intentos llevamos. Bajo la imagen, se puede observar una entrada de texto, donde tendremos que introducir el nombre del club al que creemos que pertenece ese fragmento. Para introducir el nombre del club, el usuario comenzará a escribir y le saldrá un listado de opciones con el nombre completo de los clubs que en su nombre contienen la cadena de texto escrita por el usuario. Una vez en la lista de opciones se encuentre el club del que el usuario cree que es el escudo, el usuario deberá escogerlo en la lista de opciones para poder enviar el nombre completo del club sin errores. Con el nombre seleccionado, el usuario puede enviar su intento.
            <div class="pc-view">
                <img class="manual" src="/images/manual/9.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/9.jpeg"/>
            </div>
            Según si ha acertado o no, pueden ocurrir dos cosas, si el usuario no ha acertado, habrá gastado un intento y se lo redireccionará a la misma página, donde se destapará un nuevo pedazo para que pueda volver a probar con la nueva pista:
            <div class="pc-view">
                <img class="manual" src="/images/manual/10.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/10.jpeg"/>
            </div>
            Sin embargo, si el usuario ha acertado, se lo redirigirá a la siguiente pantalla, que le indicará que ha ganado y le mostrará el escudo completo del equipo.
            <div class="pc-view">
                <img class="manual" src="/images/manual/11.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/11.jpeg"/>
            </div>
            Si un jugador gasta todos sus intentos y no consigue adivinar a que club pertenece el escudo, también será redireccionado a una página con el resultado de la partida donde se le indicará que ha perdido y se le mostrará el escudo completo para que, de cara a una próxima partida lo conozca.
            <div class="pc-view">
                <img class="manual" src="/images/manual/12.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/12.jpeg"/>
            </div>
            <a class="buttom" href="/manual/LG">Manual 1vs1 local</a>
            <a class="buttom" href="/manual">Manual de la aplicación</a>
            <a class="buttom" href="/manual/OG">Manual de partidas en línea</a>
        </div>
    </div>
</CUPES:layout>
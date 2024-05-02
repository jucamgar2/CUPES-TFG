<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Manual">

    <div class="block">
        <div class="center-items">
        <div class="manual">
            <h1>Manual de 1vs1 local</h1>
            Para jugar una partida 1vs1 local, primero tendremos que dirigirnos al selector de modos de juego que anteriormente hemos comentado y pulsar sobre el botón 1vs1 local. Es importante recordar que para jugar a este modo tampoco hace falta haber iniciado sesión y que no hay apartado estadístico para este modo. Tras pulsar el botón, observaremos la siguiente pantalla:
            <div class="pc-view">
                <img class="manual" src="/images/manual/13.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/13.jpeg"/>
            </div>
            Una vez que se rellene el anterior formulario, será el turno del jugador 1 y, por lo tanto, este jugador deberá tratar de adivinar el escudo que le ha tocado. Como se puede ver en la siguiente pantalla, se indica el nombre del jugador que debe contestar actualmente y el número de intentos que ha gastado junto al número de intentos total que tiene.
            <div class="pc-view">
                <img class="manual" src="/images/manual/14.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/14.jpeg"/>
            </div>
            A partir de este momento, el jugador 1 utilizará sus intentos y podrán ocurrir dos cosas, que el jugador consiga acertar el escudo o que el jugador gaste sus cuatro intentos y no consiga adivinar el escudo. Por lo tanto, no podrá ganar, como mucho podrá empatar en el caso de que el jugador 2 tampoco sea capaz de adivinar su escudo.
            <p></p>Cuando el jugador falle, se mostrará un nuevo pedazo y se le notificará que no ha acertado:
            <div class="pc-view">
                <img class="manual" src="/images/manual/15.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/15.jpeg"/>
            </div>
            Tanto si acierta como si gasta sus intentos, pasará a ser el turno del jugador 2 y se podrá observar que la frase es el turno de “x”, ha cambiado. 
            <div class="pc-view">
                <img class="manual" src="/images/manual/16.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/16.jpeg"/>
            </div>
            El escudo de ambos jugadores se escoge de forma simultánea y existe la posibilidad de que el escudo de ambos jugadores sea el mismo, aunque la probabilidad sea muy pequeña, concretamente de 1/127.
            <p></p>Una vez que comience el turno del jugador 2, este podrá empezar a intentar adivinar su escudo y cuando adivine el escudo o gaste sus intentos, la aplicación determinará el ganador de la siguiente forma:
            <ul class="manual">
                <li class="manual">Si solo un jugador adivina el escudo, este ganará.</li>
                <li class="manual">Si ambos jugadores no adivinan su escudo, empatarán.</li>
                <li class="manual">Si los dos jugadores adivinan el escudo, ganará quien menos intentos haya necesitado, en caso de empate en el número de intentos, el criterio para determinar el ganador será el tiempo, resultando como ganador quien menos tiempo haya necesitado. En caso de empate en tiempo e intentos, el resultado de la partida será un empate.</li>
            </ul>
            Con la partida terminada y el ganador de esta determinado, se mostrará la siguiente pantalla donde podremos ver las estadísticas de la partida.
            <div class="pc-view">
                <img class="manual" src="/images/manual/17.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/17.jpeg"/>
            </div>
            <a class="buttom" href="/">Inicio</a>
            <a class="buttom" href="/manual">Manual de la aplicación</a>
            <a class="buttom" href="/manual/GA">Manual partidas en solitario</a>
            <a class="buttom" href="/manual/OG">Manual de partidas en línea</a>
        </div>
        </div>
    </div>
</CUPES:layout>
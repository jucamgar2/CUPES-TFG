<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Manual">

    <div class="block">
        <div class="center-items">
        <div class="manual">
            <h1>Manual del modo de juego en línea</h1>
            Para jugar una partida en línea en primer lugar tendremos que habernos identificado en el sistema previamente y tras identificarnos, tendremos dos opciones, podremos crear una partida o unirnos a una que ya este creada y no haya empezado. Para cualquiera de las opciones, tendremos que ir a la página de modos de juego y ahí podremos seleccionar la opción que más nos convenza. Si decidimos crear una partida, la aplicación la creará y nos llevará al lobby. 
            <div class="pc-view">
                <img class="manual" src="/images/manual/18.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/18.jpeg"/>
            </div>
            Sin embargo, si decidimos unirnos a una partida, puede que tengamos que esperar y mientras esperamos aparecerá el siguiente mensaje en la pantalla, aunque si hay partidas en a las que todavía no se haya unido un segundo jugador, la pantalla no aparecerá, sino que directamente seremos direccionados al lobby de una partida. En la pantalla de espera, tendremos un pasatiempo que constará de un juego de memoria en el que le tendremos que dar la vuelta a las siguientes cartas de dos en dos para encontrar las parejas de escudos.
            <div class="pc-view">
                <img class="manual" src="/images/manual/19.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/19.jpeg"/>
            </div>
            Es importante tener en cuenta que tanto si queremos crear una partida como si queremos unirnos a una que puede que la aplicación tarde en conseguir hacer el emparejamiento ya que si alguien crea una partida, es necesario que alguien más quiera unirse a una partida, por lo que si todos los usuarios de la aplicación deciden crear una partida, no habrá nadie que se una a esas partida y por lo tanto estas nunca comenzarán, por lo se recomienda encarecidamente a los usuarios que si crean una partida y nadie se une, prueben a unirse a una partida, porque puede que el flujo de jugadores que crean partidas sea mayor que el flujo de jugadores que se unen a partidas. En el caso opuesto también se recomienda que, si se busca unirse a una partida y tarda mucho, se pruebe a crear una partida.
            <p></p>Una vez que un jugador haya creado la partida y otro se haya unido, aparecerá el siguiente lobby, donde ambos jugadores podrán decidir cuando están listos y podrán abandonar la partida antes de comenzar. Si la partida la abandona el jugador que la crea, esta partida desaparecerá y nunca se jugará, sin embargo, si quien abandona es el jugador que se ha unido a ella, el emparejamiento seguirá funcionando y se podrá unir otro jugador.
            <div class="pc-view">
                <img class="manual" src="/images/manual/20.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/20.jpeg"/>
            </div>
            Una vez que ambos jugadores estén listos, la partida habrá comenzado y ambos serán redireccionados automáticamente a la página donde se jugará la partida, en la página se les indicará el número de escudos que llevan y el número de intentos, mientras que se muestra el escudo cubierto con las pistas de la partida.
            <div class="pc-view">
                <img class="manual" src="/images/manual/21.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/21.jpeg"/>
            </div>
            Cada vez que el jugador acierte se mostrará el siguiente escudo hasta que adivine los tres:
            <div class="pc-view">
                <img class="manual" src="/images/manual/22.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/22.jpeg"/>
            </div>
            Cada vez que falle, se mostrará un pedazo más hasta que se hayan utilizado 4 intentos, si tras cuatro intentos no se ha conseguido acertar el escudo, se pasará al siguiente.
            <div class="pc-view">
                <img class="manual" src="/images/manual/23.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/23.jpeg"/>
            </div>
            Una vez que un jugador gaste todos sus intentos o adivine los tres escudos, pasará a una página donde tendrá que esperar hasta que el otro jugador termine, para que las espera sea más amena, en esta pantalla también se ha incluido el pasatiempo donde hay que levantar las cartas de dos en dos para conseguir parejas.
            <div class="pc-view">
                <img class="manual" src="/images/manual/24.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/24.jpeg"/>
            </div>
            Cuando ambos jugadores terminen, el sistema determinará el ganador de la partida de la siguiente forma:
            <ul class="manual">
                <li class="manual">Gana quien más escudos acierte.</li>
                <li class="manual">En caso de empate a aciertos, gana quien menos intentos haya necesitado.</li>
                <li class="manual">En caso de empate a intentos, gana quien menos tiempo haya tardado.</li>
                <li class="manual">En caso de empate en tiempo y aciertos, el resultado de la partida será empate.</li>
            </ul>
            <div class="pc-view">
                <img class="manual" src="/images/manual/25.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/25.jpeg"/>
            </div>
            En cualquier momento de la partida, un jugador podrá decidir abandonarla pulsando el botón rojo de abandonar partida, lo que provocará automáticamente su derrota y la victoria del otro jugador que será redirigido automáticamente a la pantalla de resultado de la partida.
            <div class="pc-view">
                <img class="manual" src="/images/manual/26.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/26.jpeg"/>
            </div>
            <a class="buttom" href="/manual/GA">Manual partidas en solitario</a>
            <a class="buttom" href="/manual">Manual de la aplicación</a>
            <a class="buttom" href="/manual/LG">Manual 1vs1 local</a>
        </div>
    </div>
    </div>
</CUPES:layout>
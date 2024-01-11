<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Manual">

    <div class="block">
        <div class="center-items">
        <div class="manual">
            <h1>Manual de usuario</h1>
            Una vez se accede a la aplicación web, la página principal que se puede observar es la siguiente:
            <div class="pc-view">
                <img class="manual" src="/images/manual/1.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/1a.jpeg"/>
                Para ver la barra de navegación en dispositivos móviles, se debe pulsar el botón de la esquina superior derecha:
                <img class="manual" src="/images/manual/mobile/1b.jpeg"/>
            </div>
            Como se puede ver en tenemos una barra de navegación que siempre estará presente y podemos usarla como acceso rápido a las siguientes secciones:
            <ul class="manual">
                <li class="manual">Inicio, nos lleva a la página principal, aunque para acceder a la pantalla principal también podemos pulsar sobre el logo de CUPES de la barra de navegación.</li>
                <li class="manual">Estadísticas, nos llevará al apartado de estadísticas, que dependiendo de si hemos iniciado sesión o no, nos mostrará nuestras estadísticas individuales o solo las estadísticas generales de la aplicación.</li>
                <li class="manual">Jugar, nos lleva al apartado donde podremos seleccionar el tipo de partida que queremos jugar.</li>
                <li class="manual">Manual, es la sección donde podremos consultar el manual en línea siempre que queramos.</li>
                <li class="manual">Si no hemos iniciado sesión, observamos una opción para acceder al formulario de registro y otra opción para acceder al formulario para iniciar sesión.</li>
                <li class="manual">En el caso de que hayamos iniciado sesión, nos aparecerá una opción para cerrar sesión.</li>
            </ul> 
            Los jugadores podrán registrarse en cualquier momento a través de este formulario:
            <div class="pc-view">
                <img class="manual" src="/images/manual/2.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/2.jpeg"/>
            </div>
            Una vez registrados podrán iniciar sesión:
            <div class="pc-view">
                <img class="manual" src="/images/manual/3.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/3.jpeg"/>
            </div>
            En cualquier momento, los usuarios podrán consultar el apartado de estadísticas y si han iniciado sesión, no solo se mostrarán las estadísticas generales, sino que se les mostrará sus propias estadísticas:
            <div class="pc-view">
                <img class="manual" src="/images/manual/4.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/4.jpeg"/>
            </div>
            Tras iniciar sesión, los jugadores siempre podrán acceder a su perfil donde se les mostrará sus datos y además sus propias estadísticas:
            <div class="pc-view">
                <img class="manual" src="/images/manual/5.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/5.jpeg"/>
            </div>
            Y desde el perfil se podrá acceder al formulario de edición del perfil:
            <div class="pc-view">
                <img class="manual" src="/images/manual/6.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/6.jpeg"/>
            </div>
            Como la aplicación consta de varios modos de juegos, cada modo de juego tendrá su propio manual. En primer lugar, en la siguiente pantalla podemos seleccionar el modo de juego.
            <div class="pc-view">
                <img class="manual" src="/images/manual/7.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/7.jpeg"/>
            </div>
            Para ir a los manuales de los distintos modos de juego:
            <a class="buttom" href="/manual/GA">Partidas en solitario</a>
            <a class="buttom" href="/manual/LG">1vs1 local</a>
            <a class="buttom" href="/manual/OG">Partidas en línea</a>
        </div>
    </div>
        
    </div>

</CUPES:layout>
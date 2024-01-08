<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Manual">

    <div class="block">
        <div class="manual">
            <h1>Manual de administración</h1>
            Si iniciamos sesión con una cuenta que tenga permisos de administrador, podremos acceder a todas las funcionalidades explicadas en el manual general y adicionalmente se podrá acceder a las funciones del módulo de administración, para obtener permisos de administrador, podemos iniciar sesión con las siguientes credenciales: admin 1111. Tras iniciar sesión, podremos observar que en la barra de navegación a aparecido una nueva opción.
            <div class="pc-view">
                <img class="manual" src="/images/manual/27.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/27.jpeg"/>
            </div>
            Si pulsamos sobre administración, nos llevará a la siguiente sección:
            <div class="pc-view">
                <img class="manual" src="/images/manual/28.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/28.jpeg"/>
            </div>
            El apartado de administrar jugadores nos permitirá ver un listado paginado de todos los jugadores registrados en la aplicación, permitiéndonos deshabilitar sus cuentas para que no puedan acceder a la aplicación si lo creemos conveniente. Igual que podemos deshabilitarlos, en cualquier momento podemos volver a habilitarlos.
            <div class="pc-view">
                <img class="manual" src="/images/manual/29.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/29.jpeg"/>
            </div>
            Como se puede observar, también contamos con un buscador que facilitará la búsqueda de jugadores cuando la aplicación crezca y haya muchos usuarios.
            <p></p>Si pulsamos sobre sus nombres, podemos acceder a sus perfiles para consultar todos sus datos y sus estadísticas:
            <div class="pc-view">
                <img class="manual" src="/images/manual/30.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/30.jpeg"/>
            </div>
            Como hemos iniciado sesión como administradores si accedemos al perfil de alguien, tendremos un botón para exportar sus estadísticas de las partidas en solitario en formado .json. Esto nos permitirá estudiar las estadísticas de los jugadores.
            <p></p>Además, podremos acceder a un listado similar al de los jugadores, pero de las imágenes registradas en la aplicación, este listado también está paginado y tendrá un buscador. En este caso si deshabilitamos una imagen esta ya no podrá ser seleccionada en las siguientes partidas de todos los modos de juego.
            <div class="pc-view">
                <img class="manual" src="/images/manual/31.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/31.jpeg"/>
            </div>
            Si pulsamos sobre el ojo, accederemos a los datos de una imagen:
            <div class="pc-view">
                <img class="manual" src="/images/manual/32.jpg"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/32.jpeg"/>
            </div>
            En primer lugar, podremos observar la imagen y los 16 trozos que la componen, debajo podemos ver los datos de la imagen y alguna estadística, también tenemos un botón para deshabilitar/habilitar la imagen, una opción para exportar todos los datos de las partidas en solitario en las que ha aparecido la imagen a un archivo .json y por ultimo una muestra de cuales de los trozos de la imagen son válidos para ser pistas en una partida (en color verde) y los trozos que no son válidos ya que no aportan suficiente información como pistas (en rojo).
            <p></p>La aplicación también proporciona un formulario para que los administradores puedan añadir nuevas imágenes a la aplicación y que estas puedan salir en las partidas.
            <div class="pc-view">
                <img class="manual" src="/images/manual/33.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/33.jpeg"/>
            </div>
            Además, la aplicación tiene un apartado de estadísticas de las imágenes para que los administradores puedan sacar conclusiones de que tipo de escudos reconocen mejor los usuarios de la aplicación. Desde este apartado también se pueden exportar datos de las partidas en solitario a un archivo .json permitiendo que los administradores escojan en qué periodo de fechas quieren extraer las estadísticas.
            <div class="pc-view">
                <img class="manual" src="/images/manual/34.png"/>
            </div>
            <div class="mobile-view">
                <img class="manual" src="/images/manual/mobile/34.jpeg"/>
            </div>
            <a class="buttom" href="/manual">Manual de la aplicación</a>

        </div>
    </div>
</CUPES:layout>
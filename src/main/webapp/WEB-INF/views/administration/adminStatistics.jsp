<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<CUPES:layout title="Estadísticas">
    <script>
        $(function () {
            $("#startDate").datepicker({dateFormat: 'dd/mm/yy' });
        });
        $(function () {
            $("#endDate").datepicker({dateFormat: 'dd/mm/yy' });
        });
    </script>
            <h1>Estadísticas para el administrador:</h1>
            <table class="statistics-table">
                <thead>
                    <tr>
                        <th>Estadísticas por imagenes del modo un jugador</th>
                        <th>Estadísticas generales</th>
                        <th>Con un intento</th>
                        <th>Con dos intentos</th>
                        <th>Con tres intentos</th>
                        <th>Con cuatro intentos</th>
                    </tr>
                </thead>
                <tr>
                    <td>Porcentaje de acierto en escudos de españa:</td>
                    <td><c:out value="${spainSuccess}"/></td>
                    <td><c:out value="${spainSuccess1}"/></td>
                    <td><c:out value="${spainSuccess2}"/></td>
                    <td><c:out value="${spainSuccess3}"/></td>
                    <td><c:out value="${spainSuccess4}"/></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos de alemania:</td>
                    <td><c:out value="${germanySuccess}" /></td>
                    <td><c:out value="${germanySuccess1}" /></td>
                    <td><c:out value="${germanySuccess2}" /></td>
                    <td><c:out value="${germanySuccess3}" /></td>
                    <td><c:out value="${germanySuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos de francia:</td>
                    <td><c:out value="${franceSuccess}" /></td>
                    <td><c:out value="${franceSuccess1}" /></td>
                    <td><c:out value="${franceSuccess2}" /></td>
                    <td><c:out value="${franceSuccess3}" /></td>
                    <td><c:out value="${franceSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos de italia:</td>
                    <td><c:out value="${italySuccess}" /></td>
                    <td><c:out value="${italySuccess1}" /></td>
                    <td><c:out value="${italySuccess2}" /></td>
                    <td><c:out value="${italySuccess3}" /></td>
                    <td><c:out value="${italySuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos de inglaterra:</td>
                    <td><c:out value="${ukSuccess}" /></td>
                    <td><c:out value="${ukSuccess1}" /></td>
                    <td><c:out value="${ukSuccess2}" /></td>
                    <td><c:out value="${ukSuccess3}" /></td>
                    <td><c:out value="${ukSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos de Estados Unidos:</td>
                    <td><c:out value="${usaSuccess}" /></td>
                    <td><c:out value="${usaSuccess1}" /></td>
                    <td><c:out value="${usaSuccess2}" /></td>
                    <td><c:out value="${usaSuccess3}" /></td>
                    <td><c:out value="${usaSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Pocentaje de acierto en escudos masculinos </td>
                    <td><c:out value="${maleSuccess}" /></td>
                    <td><c:out value="${maleSuccess1}" /></td>
                    <td><c:out value="${maleSuccess2}" /></td>
                    <td><c:out value="${maleSuccess3}" /></td>
                    <td><c:out value="${maleSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos femeninos</td>
                    <td><c:out value="${femaleSuccess}" /></td>
                    <td><c:out value="${femaleSuccess1}" /></td>
                    <td><c:out value="${femaleSuccess2}" /></td>
                    <td><c:out value="${femaleSuccess3}" /></td>
                    <td><c:out value="${femaleSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Procentaje de acierto en clubes de primeras divisiones</td>
                    <td><c:out value="${firstDivisionSuccess}" /></td>
                    <td><c:out value="${firstDivisionSuccess1}" /></td>
                    <td><c:out value="${firstDivisionSuccess2}" /></td>
                    <td><c:out value="${firstDivisionSuccess3}" /></td>
                    <td><c:out value="${firstDivisionSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en clubes de segunda division</td>
                    <td><c:out value="${secondDivisionSuccess}" /></td>
                    <td><c:out value="${secondDivisionSuccess1}" /></td>
                    <td><c:out value="${secondDivisionSuccess2}" /></td>
                    <td><c:out value="${secondDivisionSuccess3}" /></td>
                    <td><c:out value="${secondDivisionSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos con Nombres</td>
                    <td><c:out value="${nameSuccess}" /></td>
                    <td><c:out value="${nameSuccess1}" /></td>
                    <td><c:out value="${nameSuccess2}" /></td>
                    <td><c:out value="${nameSuccess3}" /></td>
                    <td><c:out value="${nameSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos sin Nombres</td>
                    <td><c:out value="${noNameSuccess}" /></td>
                    <td><c:out value="${noNameSuccess1}" /></td>
                    <td><c:out value="${noNameSuccess2}" /></td>
                    <td><c:out value="${noNameSuccess3}" /></td>
                    <td><c:out value="${noNameSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos con iniciales</td>
                    <td><c:out value="${initialsSuccess}" /></td>
                    <td><c:out value="${initialsSuccess1}" /></td>
                    <td><c:out value="${initialsSuccess2}" /></td>
                    <td><c:out value="${initialsSuccess3}" /></td>
                    <td><c:out value="${initialsSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos sin iniciales</td>
                    <td><c:out value="${noInitialsSuccess}" /></td>
                    <td><c:out value="${noInitialsSuccess1}" /></td>
                    <td><c:out value="${noInitialsSuccess2}" /></td>
                    <td><c:out value="${noInitialsSuccess3}" /></td>
                    <td><c:out value="${noInitialsSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos con años</td>
                    <td><c:out value="${yearSuccess}" /></td>
                    <td><c:out value="${yearSuccess1}" /></td>
                    <td><c:out value="${yearSuccess2}" /></td>
                    <td><c:out value="${yearSuccess3}" /></td>
                    <td><c:out value="${yearSuccess4}" /></td>
                </tr>
                <tr>
                    <td>Porcentaje de acierto en escudos sin años</td>
                    <td><c:out value="${noYearSuccess}" /></td>
                    <td><c:out value="${noYearSuccess1}" /></td>
                    <td><c:out value="${noYearSuccess2}" /></td>
                    <td><c:out value="${noYearSuccess3}" /></td>
                    <td><c:out value="${noYearSuccess4}" /></td>
                </tr>
            </table>
            <div>
                <p class="game">Selecciona un rango de fechas para exportar las estadísticas de las partidas</p>
                <form:form modelAttribute="dateRangeForm">
                    <div>
                        <label for="startDate">Fecha de inicio:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="startDate" name="startDate">
                    </div>
                    <div>
                        <label for="endDate">Fecha de fin:</label>
                    </div>
                    <div>
                        <input class="input-large"type="text" id="endDate" name="endDate">
                    </div>
                    <button class="buttom" type="submit">Exportar</button>
                </form:form>
            </div>


</CUPES:layout>
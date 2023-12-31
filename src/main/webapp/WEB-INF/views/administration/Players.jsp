<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <form id="buscarJugadorForm" onsubmit="return sendForm()">
                <label for="username">Buscar jugadores:</label>
                <input class="input-large" type="text" id="username" name="username">
                <button type="submit" class="buttom">Buscar</button>
            </form>
            <h1>Estos son los usuarios</h1>
            <table border="1">
                <thead>
                    <tr>
                        <th>Nombre de usuario</th>
                        <th>Mail</th>
                        <th>Estado</th>
                        <th>Deshabilitar</th>
                        <th>Habilitar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="player" items="${players}">
                        <tr>
                            <td>
                             <a href="/players/profile/${player.getUsername()}"><c:out value="${player.getUsername()}"/></a>    
                            </td>
                            <td><c:out value="${player.getMail()}"/></td>
                            <td>
                                <c:if test="${!player.getEnabled()}">
                                    <a style="color:red;"><i class="fa fa-window-close"></i></a>
                                </c:if>
                                <c:if test = "${player.getEnabled()}">
                                    <a style="color:green"><i class="fa fa-check-circle"></i></a>
                                </c:if>
                            </td>
                            <td><a href="/administration/players/disable/${player.getUsername()}" ><i class="fa fa-exclamation"></i></a></td>
                            <td><a href="/administration/players/enable/${player.getUsername()}"><i class="fa fa-play"></i></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div>
                <p class="game">Páginas:</p>
                <c:forEach begin="0" step="1" end="${totalPages-1}" var="page">
                    <td>
                        <a class="buttom" style="margin-left: 5px;" href="/administration/players?page=${page}&username=${username}" value = "${page}"><c:out value = "${page+1}"/></a>
                    </td>
                </c:forEach>
            </div>    
            
            
            
        </div>
    </div>
</CUPES:layout>
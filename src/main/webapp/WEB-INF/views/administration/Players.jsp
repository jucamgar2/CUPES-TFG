<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1>Estas son los usuarios</h1>
            <table border="1">
                <thead>
                    <tr>
                        <th>Nombre de usuario</th>
                        <th>Mail</th>
                        <th>Estado</th>
                        <th>Deshabilitar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="player" items="${players}">
                        <tr>
                            <td><c:out value="${player.getUsername()}"/></td>
                            <td><c:out value="${player.getMail()}"/></td>
                            <td>
                                <c:if test="${!player.getEnabled()}">
                                    <a style="color: red;"><i class="fa fa-close"></i></a>
                                </c:if>
                                <c:if test = "${player.getEnabled()}">
                                    <a style="color:green"><i class="fa fa-check-circle"></i></a>
                                </c:if>
                            </td>
                            <td><a href="/administration/players/delete/${player.getUsername()}" ><i class="fas fa-trash"></i></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div>
                <p class="game">Páginas:</p>
                <c:forEach begin="0" step="1" end="${totalPages-1}" var="page">
                    <td>
                        <a class="buttom" style="margin-left: 5px;" href="/administration/players?page=${page}" value = "${page}"><c:out value = "${page+1}"/></a>
                    </td>
                </c:forEach>
            </div>    
            
            
            
        </div>
    </div>
</CUPES:layout>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <h1>Estas son las imagenes que pueden aparecer en la aplicación</h1>
            <table border="1">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Imagen</th>
                        <th>Eliminar</th>
                        <th>Ver datos</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="image" items="${images}">
                        <tr>
                            <td>${image.getName()}</td>
                            <td><img src="/images/Logo/${image.getResourceName()}.jpg" alt="${imagen.nombre}" width="100" height="100"></td>
                            <td>
                                <c:if test="${image.getEnabled()}">
                                    <a href="/administration/images/disable/${image.getId()}" ><i class="fas fa-times"></i></a>
                                </c:if>
                                <c:if test="${!image.getEnabled()}">
                                    <a href="/administration/images/enable/${image.getId()}" ><i class="fas fa-check"></i></a>
                                </c:if>
                            </td>
                            <td><a href="/administration/images/view/${image.getId()}" ><i class="fas fa-eye"></i></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div>
                <p class="game">Páginas:</p>
                <c:forEach begin="0" step="1" end="${totalPages-1}" var="page">
                    <td>
                        <a class="buttom" style="margin-left: 5px;" href="/administration/images?page=${page}" value = "${page}"><c:out value = "${page+1}"/></a>
                    </td>
                </c:forEach>
            </div>    
            
            
            
        </div>
    </div>
</CUPES:layout>
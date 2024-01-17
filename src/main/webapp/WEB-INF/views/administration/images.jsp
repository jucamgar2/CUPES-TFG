<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Listado de imagenes">
    <div class="block">
            <h1 class="title">Estas son las imágenes que pueden aparecer en la aplicación</h1>

            <form id="buscarImageForm" onsubmit="return sendImageForm()">
                <label for="name">Buscar imagenes:</label>
                <input class="input-large" type="text" id="name" name="name">
                <button type="submit" class="buttom">Buscar</button>
            </form>
            <c:if test="${succes}">
                <div id="succes">
                    <div class="succes-notification">
                        <span>Imagen añadida correctamente</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>

            <c:if test="${enabled}">
                <div id="succes">
                    <div class="succes-notification">
                        <span>Imagen habilitada correctamente</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>

            <c:if test="${disabled}">
                <div id="succes">
                    <div class="error-notification">
                        <span>Imagen deshabilitada correctamente</span>
                        <button class="close-button">×</button>
                    </div>
                </div>
            </c:if>


            <div class="block">
                <table class="statistics-table">
                    <thead>
                        <tr>
                            <th style="width: 40%;">Nombre</th>
                            <th style="width: 20%;">Imagen</th>
                            <th style="width: 20%;">Deshabilitar/Habilitar</th>
                            <th style="width: 20%;">Ver datos</th>
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
            </div>
            <c:if test="${!images.isEmpty()}">
                <div>
                    <p class="game">Páginas:</p>
                    <c:forEach begin="0" step="1" end="${totalPages-1}" var="page">
                        <td>
                            <a class="buttom" style="margin-left: 5px;" href="/administration/images?page=${page}&name=${name}" value = "${page}"><c:out value = "${page+1}"/></a>
                        </td>
                    </c:forEach>
                </div>    
            </c:if>
    </div>
    <script src="/js/error_script.js"></script>
</CUPES:layout>
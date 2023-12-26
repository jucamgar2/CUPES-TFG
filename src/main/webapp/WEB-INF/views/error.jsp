<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
        <div class="theBody">
            <c:out value="${error}" />
        </div>
    </div>
</CUPES:layout>
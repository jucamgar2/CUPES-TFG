<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="CUPES" tagdir="/WEB-INF/tags" %>

<CUPES:layout title="Página de Inicio">
    <div class="doBody">
    <h1>Player Form</h1>
    <form action="${pageContext.request.contextPath}" method="post">
        <label for="username">username:</label>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Name:</label>
        <input type="text" id="password" name="password" required>
        <br>
        <input type="submit" value="Save">
    </form>
</div>
</CUPES:layout>
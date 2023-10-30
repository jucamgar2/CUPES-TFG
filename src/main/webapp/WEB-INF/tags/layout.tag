<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag description="Global Layout" %>
<%@ attribute name="title" required="true" rtexprvalue="true" description="Title for the page" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
    <title>${title}</title>

    <link rel="stylesheet" type="text/css" href="/css/CUPES.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    
    <style>
    img.game{
       ${imageStyle}
    }
    </style>
</head>
<body>
  <nav class="navbar">
   
    <div class="container">
    <p class="logo">CUPES</p>
     <input style="display:none" type="checkbox" id="check">
    <label for="check" class="checkbtn">
      <i class="fas fa-bars"></i>
    </label>
     
      <ul class="menu">
        
        <li><a class="inicio-link" href="/"><i class="fas fa-home"></i>  Inicio</a></li>
        <li><a class="statistics-link" href="/statistics"><i class=" 	fas fa-poll"></i> Estad&iacute;sticas</a></li>
        <li><a class="jugar-link" href="/game"><i class="fas fa-gamepad"></i>  Jugar</a></li>
        <li><a class="manual-link" href="#"><i class="fas fa-book"></i> Manual</a></li>
        <sec:authorize access="!isAuthenticated()">
          <li><a class="registrarse-link" href="/players/new"><i class="fas fa-edit"></i> Registrarse</a></li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
          <li><a class="iniciar-link" href="/login"><i class="fas fa-sign-in-alt"></i> Iniciar sesi&oacute;n</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
          <li><a class="cerrar-link" href="/logout"><i class="fas fa-sign-in-alt"></i> Cerrar sesi&oacute;n</a></li>
        </sec:authorize>
      </ul>

      <script>
        const currentUrl = window.location.pathname;
        const links = document.querySelectorAll('.menu a');
        links.forEach(link => {
          const href = link.getAttribute('href');
          if (currentUrl === href ) {
            link.classList.add('active');
          } else if (currentUrl.includes(href) && href !== "/") {
            link.classList.add('active');
          }
        });
      </script>

    </div>
  </nav>


    <div class="content">
        
        <jsp:doBody />
    </div>
    
</body>
</html>
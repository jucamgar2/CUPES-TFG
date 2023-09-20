<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag description="Global Layout" %>
<%@ attribute name="title" required="true" rtexprvalue="true" description="Title for the page" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <link rel="stylesheet" type="text/css" href="/css/CUPES.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    
    <title><%= title %></title>
    <% if ("game".equals(title)) { %>
        <script>
          window.onbeforeunload = function(){
            return "¿Estas seguro de que quieres salir? Perderas la partida";
          }
        </script>
    <% } %>
    <style>
    img.game{
       ${imageStyle}
    }
    </style>
</head>
<body>
    <nav class="navbar">
              <input type="checkbox" id="check">
      <label for="check" class="checkbtn">
        <i class="fas fa-bars"></i>
      </label>
      <label class="logo">CUPES</label>
      <ul>
        <li><a class="active" href="/"><i class="fas fa-home"></i> Inicio</a></li>
        <li><a href="/game/select"><i class="fas fa-gamepad"></i> Jugar</a></li>
        <li><a href="#"><i class="fas fa-book"></i> Manual</a></li>
        <li><a href="/players/new"><i class="fas fa-edit"></i>Registrarse</a></li>
      </ul>
    </nav>

    <div class="content">
        
        <jsp:doBody />
    </div>
    
</body>
</html>
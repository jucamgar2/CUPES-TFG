document.addEventListener('DOMContentLoaded', function() {
    var iconoMostrar = document.getElementById('iconoMostrar');
    var passwordInput = document.getElementById('password');

    iconoMostrar.addEventListener('click', function() {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text'; 
            iconoMostrar.className = 'fas fa-eye-slash';
        } else {
            passwordInput.type = 'password'; 
            iconoMostrar.className = 'fas fa-eye'; 
        }
    });
});
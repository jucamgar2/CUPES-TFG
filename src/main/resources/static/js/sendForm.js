function sendForm() {
    var username = document.getElementById("username").value;
    var url = '/administration/players?username=' + encodeURIComponent(username);
    window.location.href = url;
    return true;
}
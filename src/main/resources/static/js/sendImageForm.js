function sendImageForm() {
    var image = document.getElementById("name").value;
    var url = '/administration/images?name=' + encodeURIComponent(image);
    window.location.href = url;
    return true;
}
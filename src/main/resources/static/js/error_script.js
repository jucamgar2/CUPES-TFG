function closeNotification(button) {
    const notification = button.parentElement;
    notification.style.display = "none";
}
const closeButtons = document.querySelectorAll(".close-button");
closeButtons.forEach(function (button) {
    button.addEventListener("click", function () {
        closeNotification(button);
    });
});
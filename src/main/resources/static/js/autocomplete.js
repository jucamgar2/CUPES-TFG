document.addEventListener("DOMContentLoaded", function() {
    var input = document.getElementById("autocompleteInput");
    var datalist = document.getElementById("logos");

    fetch("/autocomplete/logos")
        .then(response => response.json())
        .then(data => {
            data.forEach(function(word) {
                var option = document.createElement("option");
                option.value = word;
                datalist.appendChild(option);
            });
        });

    input.addEventListener("input", function() {
        var inputValue = input.value.toLowerCase();
        var options = datalist.getElementsByTagName("option");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            if (option.value.toLowerCase().startsWith(inputValue)) {
                option.hidden = false;
            } else {
                option.hidden = true;
            }
        }
    });
});
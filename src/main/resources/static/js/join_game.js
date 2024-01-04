function checkGamesStatus() {
    fetch(`/lobby`)
        .then(response => response.json())
        .then(gameJoin => {
            if (gameJoin) {
                window.location.href = `/game/onlineGame/join`;
            } else {
                setTimeout(() => checkGamesStatus(), 1000); 
            }
        })
        .catch(error => {
            console.error('Error al verificar el estado del lobby:', error);
        });
}

checkGamesStatus();
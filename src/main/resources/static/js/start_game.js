function getGameIdFromUrl() {
    const currentUrl = window.location.href;
    const match = currentUrl.match(/\/game\/onlineGame\/join\/(\d+)/);
    if (match) {
        console.log('ID de la partida:', match[1])
        return match[1];
    } else {
        console.error('No se pudo encontrar el ID de la partida en la URL actual.');
        return null;
    }
}

function checkLobbyStatus(gameId) {
    
    fetch(`/start/${gameId}`)
        .then(response => response.json())
        .then(bothPlayersFinished => {
            if (bothPlayersFinished) {
                
                window.location.href = `/game/onlineGame/play/${gameId}`;
            } else {
                setTimeout(() => checkLobbyStatus(gameId), 1000); 
            }
        })
        .catch(error => {
            console.error('Error al verificar el estado del lobby:', error);
        });
}

const gameId = getGameIdFromUrl();
if (gameId) {
    checkLobbyStatus(gameId);
}
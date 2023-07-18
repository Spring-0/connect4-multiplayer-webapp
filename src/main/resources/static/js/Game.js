/**
 * Prompt user for gameID
 * Send post request to connect game endpoint
 *
 */
function joinGameWithID(){

    const gameId = prompt("Enter game ID: ", "")
    if(gameId != null){
        window.location.href = "/game?gameID=" + gameId;
    }
}


/**
 * Allow user to join random game.
 */
function joinRandomGame(){

    postText("/game/connect/random", "")
        .then(response => {
            window.location.href = "/game?gameID=" + response["gameID"];
        })
        .catch(error => {
            console.log(error);
        })
}


/**
 * Create new game
 */
function createNewGame() {

    if (isLoggedIn()) {
        console.log("Creating a new game...")
        postText(getPlayerIDFromCookie())
            .then(response => {
                window.location.replace("/game")
                updateNames(response)
                return response;
            })
    } else{
        alert("You must be logged in to create a game.")
    }

}


/**
 * Function used to update the game state
 *
 * @param gameState
 * @param gameBoard
 */
function updateGameState(gameState, gameBoard){

    const RED_CHIP = "../images/red-chip.png";
    const YELLOW_CHIP = "../images/yellow-chip.png";

    const img = document.createElement("img");

    const row = gameState["pieceRow"];
    const col = gameState["pieceCol"];

    const cell = gameBoard.rows[row].cells[col];

    if(gameState["lastPlayedGamePiece"] === "RED"){
        img.src = RED_CHIP;
    } else{
        img.src = YELLOW_CHIP;
    }

    const playerName1 = document.getElementById("player1Name");
    const playerName2 = document.getElementById("player2Name");

    if (gameState["lastPlayedUsername"] === playerName1.innerText) {
        playerName1.style.color = "green"; // Set the color for player 1's turn
        playerName2.style.color = "white"; // Set the color for player 2's turn (default color)
    } else {
        playerName1.style.color = "white"; // Set the color for player 1's turn (default color)
        playerName2.style.color = "green"; // Set the color for player 2's turn
    }

    cell.appendChild(img);
}


function endGame(){
    window.location.replace("/"); // Send back to home page
    stompClient.disconnect(); // Close socket connection
}


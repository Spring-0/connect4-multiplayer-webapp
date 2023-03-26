/**
 * Prompt user for gameID
 * Send post request to connect game endpoint
 *
 */
function connectToExistingGame(){
    var gameId = prompt("Enter game ID: ", "")
    if(gameId != null){
        window.location.href = "/game?gameID=" + gameId;
    }
}

/**
 * Create new game
 */
function createNewGame(){

    console.log("Creating a new game...")
    postText(getPlayerIDFromCookie())
        .then(response => {
            window.location.replace("/game")
            updateNames(response)
            return response;
        })

}


/**
 * Function used to update the game state
 *
 * @param gameState
 * @param gameBoard
 */
function updateGameState(gameState, gameBoard){

    const row = gameState["pieceRow"];
    const col = gameState["pieceCol"];

    const cell = gameBoard.rows[row].cells[col];

    // TODO: Update to use game piece
    cell.textContent = gameState["lastPlayed"]["username"]

}


function endGame(){
    window.location.replace("/"); // Send back to home page
    stompClient.disconnect(); // Close socket connection
}

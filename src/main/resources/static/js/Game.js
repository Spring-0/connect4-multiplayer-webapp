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
 * Helper Function to send post request with raw text body
 *
 * @param endpoint
 * @param body
 * @returns {Promise<any>}
 */
function postText(endpoint, body) {
    return fetch(endpoint, {
        method: "POST",
        body: body
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP error " + response.status);
            }
            console.log(response.status)
            return response.json();
        })
        .then(data => {
            console.log("Response received:", data);
            return data;
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

/**
 * Helper Function to post JSON data to endpoint
 *
 * @param endpoint
 * @param data
 * @returns {Promise<any>}
 */
function postJson(endpoint, data) {
    return fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .catch(error => console.error(error));
}
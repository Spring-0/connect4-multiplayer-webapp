/**
 * Function to get player id in saved cookies
 *
 * @returns {string|null}
 */
function getPlayerIDFromCookie() {
    // Get all cookies as a string
    const cookies = document.cookie;

    // Split cookies string by semicolon and create an array
    const cookiesArray = cookies.split(';');

    // Loop through each cookie and find the one that has "playerID" as its name
    for (let i = 0; i < cookiesArray.length; i++) {
        const cookie = cookiesArray[i].trim();

        if (cookie.startsWith('userId=')) {
            // If found, return the value of "playerID"
            return cookie.substring('userId='.length, cookie.length);
        }
    }

    // If "playerID" cookie was not found, return null
    return null;
}


/**
 * Function to update player names & Game ID tags
 *
 * @param response
 */
function updateNames(response){
    document.getElementById("player1Name").innerHTML = response["player1"].username;

    if (response["player2"] && response["player2"].username) {
        // player 2 exists and has a username
        document.getElementById("player2Name").innerHTML = response["player2"].username;
    } else {
        // player 2 does not exist or does not have a username
        document.getElementById("player2Name").innerHTML = "Waiting for player...";
    }
    document.getElementById("gameID").innerHTML = response.gameID;
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


/**
 * Helper Function for sending GET requests to endpoint
 *
 * @param endpoint
 * @returns {Promise<any>}
 */
function getRaw(endpoint){
    return fetch(endpoint, {
        method: 'GET'
    })
        .then(response => {
            if(!response.ok){
                throw new Error("HTTP error: " + response.status)
            }
            return response.json();
        })
        .then(data => {
            return data;
        })
}


function addPlayerRowToLeaderboardTable(playerName, gamesPlayed, wins, losses) {
    const table = document.getElementById("leaderboard-tbody");
    const row = table.insertRow(-1); // Add row at end of table
    const nameCell = row.insertCell(0); // Add cell for player name
    const gamesPlayedCell = row.insertCell(1); // Add cell for games played
    const winsCell = row.insertCell(2); // Add cell for wins
    const lossesCell = row.insertCell(3); // Add cell for losses
    nameCell.innerHTML = playerName;
    gamesPlayedCell.innerHTML = gamesPlayed;
    winsCell.innerHTML = wins;
    lossesCell.innerHTML = losses;
}


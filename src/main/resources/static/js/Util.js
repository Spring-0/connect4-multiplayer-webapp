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
    console.log("called updateNames")
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
/**
 * Function to change the Items on nav bar based on user authentication state
 * Will be called on page load and check if a user is authenticated using their cookie
 * If authenticated Logout Button will be displayed, else Login / Register buttons will be displayed
 */
document.addEventListener("DOMContentLoaded", function () {

    const navbar = document.querySelector("#navbar");
    const loginAnchor = document.querySelector("#login-anchor");
    const registerAnchor = document.querySelector("#register-anchor");
    const logoutAnchor = document.createElement("a");

    logoutAnchor.setAttribute("class", loginAnchor.getAttribute("class"));
    logoutAnchor.innerText = "Logout";
    logoutAnchor.addEventListener("click", logout);

    // If player is authenticated
    if(getPlayerIDFromCookie() != null){
        navbar.replaceChild(logoutAnchor, loginAnchor);
        navbar.removeChild(registerAnchor);

    } else{
        navbar.replaceChild(loginAnchor, logoutAnchor);
        navbar.appendChild(registerAnchor);
    }
});


function showGameOptionPopup(){

    // Verify that user is logged in.
    if(!isLoggedIn()){
        alert("You must be logged in to join a game.");
        return;
    }

    // Declare popup
    const popup = document.createElement("div");
    popup.classList.add("popover");

    const message = document.createElement("p");
    message.innerText = "Select game type: ";
    popup.append(message)


    // Declare join random game button
    const joinRandomButton = document.createElement("button");
    joinRandomButton.innerText = "Join a random game";
    joinRandomButton.classList.add("button");

    joinRandomButton.onclick = function (){
        popup.remove();
        joinRandomGame();
    }

    // Declare Join with ID button
    const joinSpecificGameButton = document.createElement("button");
    joinSpecificGameButton.innerText = "Join with game id";
    joinSpecificGameButton.classList.add("button");

    joinSpecificGameButton.onclick = function (){
        popup.remove();
        joinGameWithID();
    }

    // Append buttons to popup
    popup.appendChild(joinSpecificGameButton);
    popup.appendChild(joinRandomButton);

    // Add popup to page
    document.body.appendChild(popup);

}



function loadLeaderboard(){
    getRaw("/player/leaderboard/top-players")
        .then(response => {
            for (let i = 0; i < 10; i++) {
                let data = response[i];
                addPlayerRowToLeaderboardTable(data[0], data[1], data[2], data[3]);
            }
        })
}


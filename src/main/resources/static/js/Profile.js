function loadProfileData(){
    const playerId = getPlayerIDFromCookie();

    if(playerId !== null){
        getRaw("/player/player-details?playerId=" + playerId)
            .then(response => {
                const stats = response["playerStatistics"]
                document.getElementById("welcome-message").textContent = `Welcome ${response["username"]}`;
                document.getElementById("games-played-value").textContent = stats["gamesPlayed"];
                document.getElementById("games-won-value").textContent = stats["wins"];
                document.getElementById("games-lost-value").textContent = stats["losses"];
            })
    } else{
        document.location.replace("/");
        alert("Must be logged in.")
    }

}
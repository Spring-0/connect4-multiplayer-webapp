
// Get all the columns from game board
const columns = document.querySelectorAll('#game-board td');

// Add click listener for each col
columns.forEach((column) => {
    column.addEventListener('click', () => {
        // Get the index of the clicked column
        const colIndex = Array.from(column.parentNode.children).indexOf(column);

        const data = {
            gameID: document.getElementById("gameID").innerHTML,
            col: colIndex,
            playerID: getPlayerIDFromCookie()
        }

        postJson("/game/playgame", data)
            .then(response =>{
                // Do nothing
            });

    });
});
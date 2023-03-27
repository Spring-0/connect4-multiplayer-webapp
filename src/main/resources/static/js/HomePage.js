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




/**
 * Function to send data to register endpoint
 *
 * @param event
 */
function register(event) {
  event.preventDefault(); // Prevent the form from submitting normally

  // Get the username and password from the form fields
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  // Construct the JSON data
  const data = {
    username: username,
    password: password
  };

  // Send the POST request
  fetch('/player/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
      .then(response => {
          if (response.status === 200) {
              window.location.replace(response.headers.get("Location"));
          } else if (response.status === 401) {
              return response.text().then(data => alert(data));
          }
      })
      .catch(error => console.error(error));
}

/**
 * Function to send data to log in endpoint
 *
 * @param event
 */
function login(event) {
  event.preventDefault(); // Prevent the form from submitting normally

  // Get the username and password from the form fields
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  // Construct the JSON data
  const data = {
    username: username,
    password: password
  };

  // Send the POST request
  fetch('/player/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
      .then(response => {
          if (response.status === 200) {
              window.location.replace(response.headers.get("Location"));
          } else if (response.status === 401) {
              return response.text().then(data => alert(data));
          }
      })
      .catch(error => console.error(error));
}


/**
 * Function to log out the user
 */
function logout(){

    fetch("/player/logout", {
        method: "POST",
        credentials: "include"
    }).then( response => {
        if (response.ok){
            location.reload();
        } else{
            console.log("Logout failed")
        }
    })
}


/**
 * Function to check if a player is logged in
 *
 * @returns {boolean}
 */
function isLoggedIn(){
    return getPlayerIDFromCookie() != null;

}


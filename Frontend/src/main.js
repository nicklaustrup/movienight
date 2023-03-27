window.onload = function() {retrieveUseName()};
function retrieveUseName() {
    var userName = window.localStorage.getItem('userName'); //searches for the user name in localStorage
    document.getElementById("userName").innerHTML = `User Name is "` + userName + `"`;
}
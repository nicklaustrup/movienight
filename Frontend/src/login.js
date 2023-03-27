function store_redirect(){ //stores items in the localStorage
    var username = document.getElementById('userName').value;
    localStorage.clear();
    window.localStorage.setItem('userName', username);
    window.location.href="user_event.html";
}
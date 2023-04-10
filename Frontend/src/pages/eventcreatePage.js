import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventCreateClient from "../api/eventcreateClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class EventCreatePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getUser', 'getUsers', 'getMovies', 'renderEvent', 'renderLogin', 'renderMenu', 'onCreate'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new EventCreateClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.getUsers();
        this.getMovies();
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderLogin);
        this.dataStore.addChangeListener(this.renderEvent);
        document.getElementById('create-form').addEventListener('submit', this.onCreate);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEvent() {
        let resultMovie = document.getElementById("movie");
        let resultRSVP = document.getElementById("RSVP");
        const movies = this.dataStore.get("movies");
        const users = this.dataStore.get("users");
        let movieHTML = "";
        let rsvpHTML = "";

        //Movie elements for the dropdown list
        if (movies) {
            for (let movie of movies) {
            {
                movieHTML += `<option id="${movie.id}">${movie.title}</option>`;
             }
        }
        resultMovie.innerHTML = movieHTML;}

        //User elements for the checkboxes
        if (users) {
            for (let rsvp of users) {
                rsvpHTML += `<tr>
                <td>${rsvp.firstName} ${rsvp.lastName}</td>
                <td><input type="checkbox" name="invited" id="${rsvp.userId}" value="${rsvp.userId}"></td>
                </tr>`;
        }
        resultRSVP.innerHTML = rsvpHTML;

        //Date should not be in the past
        let dateField = document.getElementById("date");
        dateField.min = new Date().toISOString().slice(0,new Date().toISOString().lastIndexOf(":"));
        }
    }

    async renderLogin() {
        let resultArea = document.getElementById("login");
        const user = this.dataStore.get("user");
        let userHTML = "";

        if (user) {
            userHTML += `${user.firstName} ${user.lastName} - Log out`;
            resultArea.innerHTML = userHTML;
        } else {
            resultArea.innerHTML = "No User";
        }
    }

    async renderMenu() {
        var userId = window.localStorage.getItem('userId');
        if (userId === "01677f59-d4a7-4fbc-9455-f2ef80c06839") {
            document.getElementById("menu").innerHTML = `
                <tr>
                    <td style="text-align: center;"><a href="rsvps.html">RSVP</a></td>
                    <td style="text-align: center;"><a href="eventcreate.html">CREATE EVENT</a></td>
                    <td style="text-align: center;"><a href="events.html">EVENTS</a></td>
                    <td style="text-align: center;"><a href="moviecreate.html">CREATE MOVIE</a></td>
                    <td style="text-align: center;"><a href="movies.html">MOVIES</a></td>
                    <td style="text-align: center;"><a href="usercreate.html">CREATE USER</a></td>
                    <td style="text-align: center;"><a href="users.html">USERS</a></td>
                    <td style="text-align: right;"><a href="index.html" id="login"></a></td>
                </tr>
            `;
        }
        else {
            document.getElementById("menu").innerHTML = `
                <tr>
                    <td style="text-align: center;"><a href="rsvps.html">RSVP</a></td>
                    <td style="text-align: right;"><a href="index.html" id="login"></a></td>
                </tr>
            `;
        }
    }


    // Event Handlers --------------------------------------------------------------------------------------------------

    async getUser(userId) {
        let result = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", result);
        if (result) {
            this.showMessage(`Got User!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async getUsers() {
        let result = await this.client.getUsers(this.errorHandler);
        this.dataStore.set("users", result);
        if (result) {
            this.showMessage(`Got Users!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async getMovies() {
        let result = await this.client.getMovies(this.errorHandler);
        this.dataStore.set("movies", result);
        if (result) {
            this.showMessage(`Got Movies!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        // Gathering the values from all the inputs in the form
        let eventTitle = document.getElementById("eventTitle").value;
        let movie = document.getElementById("movie");
        let movieId = movie.options[movie.selectedIndex].id;
        let date = document.getElementById("date").value;
        let active = true;
        var users = ["85de27f1-3887-4f4f-8147-2e51ef10ed20", "9a6775b9-35c8-4bde-9165-1854a69c624c", "baf28172-8aaa-415d-a62c-4217c33dcdf2"];
        var checkboxes = document.getElementsByName('invited');
        var users = [];
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                users.push(checkboxes[i].value);
            }
        }

        //Submits all the information in order to create the record
//        this.dataStore.set("eventCreate", null);
        const createdEvent = await this.client.createEvent(eventTitle, movieId, date, active, users, this.errorHandler);
        this.dataStore.set("eventCreate", createdEvent);

        if (createdEvent) {
            this.showMessage(`Created event ${createdEvent.eventId}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventCreatePage = new EventCreatePage();
    await eventCreatePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

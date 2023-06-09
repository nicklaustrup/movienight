import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import  MovieClient from "../api/moviecreateClient";


class MoviePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getUser','renderLogin', 'renderMenu', 'onCreate'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new MovieClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderLogin);
        document.getElementById('create-movie-form').addEventListener('submit', this.onCreate);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

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
        document.getElementById("menu").innerHTML = `
                  <ul>
                    <li><a href="rsvps.html">RSVP</a></li>
                    <li><a href="events.html">Event +</a>
                      <!-- First Tier Drop Down -->
                      <ul>
                        <li><a href="eventcreate.html">Create Event</a></li>
                      </ul>
                    </li>
                    <li><a href="movies.html">Movie +</a>
                      <ul>
                        <li><a href="moviecreate.html">Create Movie</a></li>
                      </ul>
                    </li>
                    <li><a href="users.html">User +</a>
                      <!-- First Tier Drop Down -->
                      <ul>
                        <li><a href="usercreate.html">Create User</a></li>
                      </ul>
                    </li>
                    <li><a href="index.html" id="login"></a></li>
                  </ul>
        `;
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

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("movieCreated", null);

        let title = document.getElementById("create-title-field").value;
        let description = document.getElementById("create-description-field").value;

        const createdMovie = await this.client.createMovie(title, description, this.errorHandler);
        this.dataStore.set("movieCreated", createdMovie);

        if (createdMovie) {
            this.showMessage(`Created ${createdMovie.title}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const moviePage = new MoviePage();
    moviePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

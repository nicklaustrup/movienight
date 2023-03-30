import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import MovieClient from "../api/movieClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class MoviePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderMovies'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-movieId-form').addEventListener('submit', this.onGet);
        document.getElementById('create-movie-form').addEventListener('submit', this.onCreate);
        this.client = new MovieClient();

        this.dataStore.addChangeListener(this.renderMovies);
        await this.client.getAllMovies();

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderMovies() {
        let resultArea = document.getElementById("movie-result");
        let movieHTML = "<ul>"

        const movies = this.dataStore.get("movie");


        if (movies) {
            for (let movie of movies){
            movieHTML += `<li>
                <h3>Title: ${movie.title}</h3>
                <h4>Summary: ${movie.description}</h4>
                </li>`
                }
            movieHTML += "</ul>";
            resultArea.innerHTML = movieHTML;
        } else {
            resultArea.innerHTML = "No Movies";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let movieId = document.getElementById("movieId-field").value;
        this.dataStore.set("movie", null);

        let result = await this.client.getMovie(movieId, this.errorHandler);
        this.dataStore.set("movie", result);
        if (result) {
            this.showMessage(`Got ${result.title}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("movie", null);

        let title = document.getElementById("create-title-field").value;
        let description = document.getElementById("create-description-field").value;


        const createdMovie = await this.client.createMovie(title, description, this.errorHandler);
        this.dataStore.set("movie", createdMovie);
        console.log("MoviePage onCreate success");

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
    await moviePage.mount();
};

window.addEventListener('DOMContentLoaded', main);

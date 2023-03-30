import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import MovieClient from "../api/movieClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class MoviePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllMovies', 'onCreate', 'getMovie','renderGetMovie', 'renderMovies'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-movieId-form').addEventListener('submit', this.getMovie);
        document.getElementById('create-movie-form').addEventListener('submit', this.onCreate);
        this.client = new MovieClient();
        this.getAllMovies();
        this.dataStore.addChangeListener(this.renderMovies);

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderMovies() {
        let resultArea = document.getElementById("movie-result");
        let movieHTML = "<ul>"

        const movies = this.dataStore.get("movies");


        if (movies) {
            for (let movie of movies){
            movieHTML += `<li>
                <h3>${movie.title}</h3>
                <p>${movie.description}</p>
                </li>`
                }
            movieHTML += "</ul>";
            resultArea.innerHTML = movieHTML;
        } else {
            resultArea.innerHTML = "No Movies";
        }
    }

    async renderGetMovie() {
        let resultArea = document.getElementById("get-movie-result");
        let movieHTML = "";

        const movie = this.dataStore.get("movie");


        if (movie) {
            movieHTML = `
                <h3>${movie.title}</h3>
                <p>${movie.description}</p>
                `;
        movieHTML += "</ul>";
        resultArea.innerHTML = movieHTML;
    }
         else {
            resultArea.innerHTML = "Movie Not Found";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async getAllMovies() {
        let result = await this.client.getAllMovies(this.errorHandler);
        this.dataStore.set("movies", result);
        if (result) {
            this.showMessage(`Got All Movies!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async getMovie(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let movieId = document.getElementById("movieId-field").value;
        this.dataStore.set("movie", null);

        let result = await this.client.getMovie(movieId, this.errorHandler);
        this.dataStore.set("movie", result);

        if (result) {
            this.showMessage(`Got ${result.title}!`)
            this.renderGetMovie();
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

        if (createdMovie) {
            this.showMessage(`Created ${createdMovie.title}!`)
            this.getAllMovies();
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

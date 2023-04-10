import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/eventClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class EventPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getEvent', 'getUser', 'getMovies', 'renderEvent', 'renderLogin', 'renderMenu', 'onUpdate'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new EventClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        var eventId = window.localStorage.getItem('eventId'); //searches for the eventId in localStorage
        this.getUser(userId);
        this.getEvent(eventId);
        this.getMovies();
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderEvent);
        this.dataStore.addChangeListener(this.renderLogin);
        document.getElementById('update-form').addEventListener('submit', this.onUpdate);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEvent() {
        let resultMovie = document.getElementById("movie");
        const event = this.dataStore.get("event");
        const movies = this.dataStore.get("movies");
        let movieHTML = "";
        if (event) {
            //Setting the values for all the inputs using the response returned by getEvent
            document.getElementById('eventId').value= event.eventId;
            document.getElementById('eventTitle').value= event.eventTitle;
            document.getElementById('date').value= event.date;
            if (event.active) {
               document.getElementById('active_round_yes').checked=true;
            }
            else {
                document.getElementById('active_round_no').checked=true;
            };

            for (let movie of movies) {
                if (movie.id === event.movieId)
                    movieHTML += `<option id="${movie.id}" selected>${movie.title}</option>`
                else
                    movieHTML += `<option id="${movie.id}">${movie.title}</option>`
            };
            resultMovie.innerHTML = movieHTML;
            let resultRSVP = document.getElementById("RSVP");
            let rsvpHTML = "";
            for (let rsvp of event.users){
                rsvpHTML += `<tr>
                <td>${rsvp.firstName} ${rsvp.lastName}</td>`;
                if (rsvp.attending) {
                    rsvpHTML += `<td><em><span style="color:#00FF00;"><strong>Yes</strong></span></em></td></tr>`;
                }
                else {
                    rsvpHTML += `<td><em><span style="color:#FF0000;"><strong>No</strong></span></em></td></td></tr>`;
                }
            }
            resultRSVP.innerHTML = rsvpHTML;
        }

        //Date should not be in the past
        let dateField = document.getElementById("date");
        dateField.min = new Date().toISOString().slice(0,new Date().toISOString().lastIndexOf(":"));
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

    async getEvent(eventId) {
        let result = await this.client.getEvent(eventId, this.errorHandler);
        this.dataStore.set("event", result);
        if (result) {
            this.showMessage(`Got Event!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async getUser(userId) {
        let result = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", result);
        if (result) {
            this.showMessage(`Got User!`)
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

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("event", null);

        // Gathering the values from all the inputs in the form
        let eventId = document.getElementById("eventId").value;
        let eventTitle = document.getElementById("eventTitle").value;
        var movie = document.getElementById("movie");
        var movieId = movie.options[movie.selectedIndex].id;
        let date = document.getElementById("date").value;
        var activeRadioButtons = document.getElementsByName('active_round');
        let active = activeRadioButtons[0].checked; //checks the first radio button which is Yes

        //Submits all the information in order to update the record
        const updatedEvent = await this.client.updateEvent(eventId, eventTitle, movieId, date, active, this.errorHandler);

        this.dataStore.set("event", updatedEvent);

        if (updatedEvent) {
            this.showMessage(`Updated event ${updatedEvent.eventId}!`)
        } else {
            this.errorHandler("Error updating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventPage = new EventPage();
    await eventPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/eventClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class EventPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getEvent', 'getUser', 'renderEvent', 'renderLogin'], this);
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
        this.dataStore.addChangeListener(this.renderEvent);
        this.dataStore.addChangeListener(this.renderLogin)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEvent() {
        let resultArea = document.getElementById("update-form");
        const event = this.dataStore.get("event");
        if (event) {
            resultArea.innerHTML = `
                <p class="form-field">
                    <input type="hidden" required class="validated-field" id="update-eventId-field" value="${event.eventId}">
                </p>
                <p class="form-field">
                    <label for="update-eventTitle-field">Title</label>
                    <input type="text" required class="validated-field" id="update-eventTitle-field" value="${event.eventTitle}">
                </p>
                <p class="form-field">
                    <label for="update-eventMovieId-field">Movie Id</label>
                    <input type="text" required class="validated-field" id="update-eventMovieId-field" value="${event.movieId}">
                </p>
                <p class="form-field">
                    <label for="update-eventTitle-field">Movie Title</label>
                    <input type="text" required class="validated-field" id="update-eventTitle-field" value="${event.title}">
                </p>
                <p class="form-field">
                    <label for="update-eventDate-field">Date</label>
                    <input type="text" required class="validated-field" id="update-eventDate-field" value="${event.date}">
                </p>
                <p class="form-field">
                    <label for="update-eventActive-field">Active</label>
                    <input type="text" required class="validated-field" id="update-eventActive-field" value="${event.active}">
                </p>
            `;
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
        } else {
            resultRSVP.innerHTML = "No RSVP";
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
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventsPage = new EventPage();
    await eventsPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

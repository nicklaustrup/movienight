import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RSVPClient from "../api/rsvpClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RSVPPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllEventRSVPs', 'getAllUserRSVPs', 'renderUserEvents', 'renderEventUsers'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-rsvp-by-userId-form').addEventListener('submit', this.getAllUserRSVPs);
        document.getElementById('get-rsvp-by-eventId-form').addEventListener('submit', this.getAllEventRSVPs);
        document.getElementById('get-rsvp-by-userId-eventId-form').addEventListener('submit', this.getRSVP);
        this.client = new RSVPClient();

        this.dataStore.addChangeListener();

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEventUsers() {
        let resultArea = document.getElementById("get-rsvp-table-body");
        let displayArea = document.getElementById("view-rsvp-details");

        let rsvpHTML = "";

        const rsvps = this.dataStore.get("eventRsvp");

        const users = rsvps.users;

        if (rsvps) {
            displayArea.innerHTML= `
            <h2>${rsvps.eventTitle}</h2>
            <h3>${rsvps.title}</h3>
            <h4>${rsvps.description}</h4>
            <P>${rsvps.date}</P>`;

            for (let user of users) {
                rsvpHTML += `<tr class="get-rsvp-row">
                <td>${user.eventId}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.attending}</td>
                <td><button type="submit">Update</button></td>
                </tr>`
            }

            resultArea.innerHTML = rsvpHTML;
        } else {
            resultArea.innerHTML = "No RSVPs found!";
        }
    }

    async renderUserEvents() {
        let resultArea = document.getElementById("get-rsvp-table-body");
        let displayArea = document.getElementById("view-rsvp-details");
        let rsvpHTML = "";

        const rsvps = this.dataStore.get("rsvps");
        const user = this.dataStore.get("user");

        if (user) {
            displayArea.innerHTML = `
            <h2>${user.firstName} ${user.lastName}</h2>
            <h4>${user.userId}</h4>`;

            for (let rsvp of rsvps) {
                rsvpHTML += `<tr class="get-rsvp-row">
                <td>${rsvp.eventId}</td>
                <td>${rsvp.eventTitle}</td>
                <td>${rsvp.title}</td>
                <td><button type="submit">Update</button></td>
                </tr>`;
            }
            resultArea.innerHTML = rsvpHTML;
        }
    }
    // Event Handlers --------------------------------------------------------------------------------------------------

    async getAllEventRSVPs(event) {
        event.preventDefault();
        let eventId = document.getElementById("get-all-rsvp-by-eventId-field").value;
        // this.dataStore.set("event-rsvp", null);

        let result = await this.client.getAllEventRSVPs(eventId, this.errorHandler);
        this.dataStore.set("eventRsvp", result);


        if (result) {
            this.showMessage(`Got All RSVPs for Event!`);
            this.renderEventUsers();
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    /**
     * Get all RSVPs for a given User ID.
     * @param userId Unique identifier for users.
     * Together these will be the primary key for the rsvp table.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The rsvp object.
     */
    async getAllUserRSVPs(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("get-all-rsvp-by-userId-field").value;
        this.dataStore.set("rsvps", null);

        let result = await this.client.getAllUserRSVPs(userId, this.errorHandler);
        this.dataStore.set("rsvps", result);

        let user = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", user);

        if (result) {
            this.showMessage(`Got RSVP!`)
            this.renderUserEvents();
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async getRSVP(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("get-rsvp-by-userId-field").value;
        let eventId = document.getElementById("get-rsvp-by-eventId-field").value;
        this.dataStore.set("rsvp", null);

        const rsvpData = await this.client.getRSVP(userId, eventId, this.errorHandler);
        this.dataStore.set("rsvp", rsvpData);

        if (rsvpData) {
            this.showMessage(`Got RSVP!`);
            this.renderGetRSVP();
        } else {
            this.errorHandler("Error getting!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const rsvpPage = new RSVPPage();
    rsvpPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

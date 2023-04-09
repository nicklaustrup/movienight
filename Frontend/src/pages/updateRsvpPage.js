import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RSVPClient from "../api/rsvpClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class UpdateRSVPPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderGetRSVP', 'getRSVP', 'updateRSVP'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        // document.getElementById('get-rsvp-by-userId-form').addEventListener('submit', this.getAllUserRSVPs);
        document.getElementById('update-rsvp-form').addEventListener('submit', this.updateRSVP);
        document.getElementById('get-rsvp-form').addEventListener('submit', this.getRSVP);
        this.client = new RSVPClient();

        // this.dataStore.addChangeListener();

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    // async renderEventUsers() {
    //     let resultArea = document.getElementById("get-rsvp-table-body");
    //     let displayArea = document.getElementById("view-rsvp-details");
    //
    //     let rsvpHTML = "";
    //
    //     const rsvps = this.dataStore.get("rsvps");
    //
    //     const users = rsvps.users;
    //
    //     if (rsvps) {
    //         displayArea.innerHTML= `
    //         <h2>${rsvps.eventTitle}</h2>
    //         <h3>${rsvps.title}</h3>
    //         <h4>${rsvps.description}</h4>
    //         <P>${rsvps.date}</P>`;
    //
    //         for (let user of users) {
    //             rsvpHTML += `<tr class="get-rsvp-row">
    //             <td>${user.eventId}</td>
    //             <td>${user.firstName} ${user.lastName}</td>
    //             <td>${user.attending}</td>
    //             <td><button type="submit">Update</button></td>
    //             </tr>`
    //         }
    //
    //         resultArea.innerHTML = rsvpHTML;
    //     } else {
    //         resultArea.innerHTML = "No RSVPs found!";
    //     }
    // }

    // async renderUserEvents() {
    //     let resultArea = document.getElementById("get-rsvp-table-body");
    //     let displayArea = document.getElementById("view-rsvp-details");
    //     let rsvpHTML = "";
    //
    //     const rsvps = this.dataStore.get("rsvps");
    //     const user = this.dataStore.get("user");
    //
    //     if (user) {
    //         displayArea.innerHTML = `
    //         <h2>${user.firstName} ${user.lastName}</h2>
    //         <h4>${user.userId}</h4>`;
    //
    //         for (let rsvp of rsvps) {
    //             rsvpHTML += `<tr class="get-rsvp-row">
    //             <td>${rsvp.eventId}</td>
    //             <td>${rsvp.eventTitle}</td>
    //             <td>${rsvp.title}</td>
    //             <td><button type="submit">Update</button></td>
    //             </tr>`;
    //         }
    //         resultArea.innerHTML = rsvpHTML;
    //     }
    // }
    async renderGetRSVP() {
        let resultArea = document.getElementById("update-rsvp-event-details");
        let tableArea = document.getElementById("update-rsvp-table-body");
        let identityArea = document.getElementById("show-user");
        let rsvpTable = "";

        const rsvps = this.dataStore.get("rsvp");
        const event = this.dataStore.get("event");
        const userIdentity = this.dataStore.get("user");
        const users = event.users;

        if (rsvps) {
            resultArea.innerHTML= `
            <h2>${event.eventTitle}</h2>
            <h3>${event.title}</h3>
            <h4>${event.description}</h4>
            <P>${event.date}</P>`;

            for (let user of users) {
                rsvpTable += `<tr class="get-rsvp-row">
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.attending}</td>
                </tr>`
            }

            tableArea.innerHTML = rsvpTable;
            identityArea.innerHTML = `<br>
            Hello ${userIdentity.firstName} ${userIdentity.lastName}!`;
        } else {
            resultArea.innerHTML = "No RSVPs found!";
        }
    }


    // Event Handlers --------------------------------------------------------------------------------------------------
    /**
     * Get all RSVPs for a given User ID.
     * @param userId Unique identifier for users.
     * Together these will be the primary key for the rsvp table.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The rsvp object.
     */
    async updateRSVP(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("get-rsvp-by-userId-field").value;
        let eventId = document.getElementById("get-rsvp-by-eventId-field").value;
        var activeRadioButtons = document.getElementsByName('active_round');
        let isAttending = activeRadioButtons[0].checked; //checks the first radio button which is Yes

        let result = await this.client.updateRSVP(userId, eventId, isAttending, this.errorHandler);
        this.dataStore.set("rsvps", result);

        let user = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", user);

        if (result) {
            this.showMessage(`Updated RSVP!`)
            await this.renderGetRSVP();
        } else {
            this.errorHandler("Error doing POST!  Try again...");
        }
    }
    async getRSVP(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("get-rsvp-by-userId-field").value;
        let eventId = document.getElementById("get-rsvp-by-eventId-field").value;
        // this.dataStore.set("rsvp", null);

        const rsvpData = await this.client.getRSVP(userId, eventId, this.errorHandler);
        const eventData = await this.client.getEvent(eventId, this.errorHandler);
        const user = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("rsvp", rsvpData);
        this.dataStore.set("event", eventData);
        this.dataStore.set("user", user);

        if (rsvpData) {
            this.showMessage(`Got RSVP!`);
            await this.renderGetRSVP();
        } else {
            this.errorHandler("Error getting!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updateRSVPPage = new UpdateRSVPPage();
    await updateRSVPPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

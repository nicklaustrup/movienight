import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/eventsClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class EventAllPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllEvents', 'getUser', 'renderEvents', 'renderLogin'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new EventClient();
        this.getAllEvents();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.dataStore.addChangeListener(this.renderEvents);
        this.dataStore.addChangeListener(this.renderLogin)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEvents() {
        let resultArea = document.getElementById("events-info");
        const events = this.dataStore.get("events");
        let eventHTML = "";

        if (events) {
            for (let event of events){
                eventHTML += `<tr>
                    <td>${event.date}</td>
                    <td>${event.eventTitle}</td>
                    <td>${event.title}</td>`;
                if (event.active) {
                    eventHTML += `<td><em><span style="color:#00FF00;"><strong>Yes</strong></span></em></td>`;
                    }
                else {
                    eventHTML += `<td><em><span style="color:#FF0000;"><strong>No</strong></span></em></td>`;
                }
                eventHTML +=`<td><input type="button" onclick="store_redirect('${event.eventId}')" value="Update" /></td>
                    </tr>`;
            }
            resultArea.innerHTML = eventHTML;
        } else {
            resultArea.innerHTML = "No Events";
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

    async getAllEvents() {
        let result = await this.client.getAllEvents(this.errorHandler);
        this.dataStore.set("events", result);
        if (result) {
            this.showMessage(`Got All Events!`)
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
    const eventsPage = new EventAllPage();
    await eventsPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

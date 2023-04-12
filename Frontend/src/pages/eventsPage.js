import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/eventsClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class EventAllPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllEvents', 'getUser', 'renderEvents', 'renderLogin', 'renderMenu'], this);
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
        this.dataStore.addChangeListener(this.renderMenu);
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
                let dateFormatted = new Date(event.date).toLocaleString();
                eventHTML += `<tr>
                    <td>${dateFormatted}</td>
                    <td>${event.eventTitle}</td>
                    <td>${event.title}</td>`;
                if (event.active) {
                    eventHTML += `<td><em><span style="color:#fd8d38;"><strong>Yes</strong></span></em></td>`;
                    }
                else {
                    eventHTML += `<td><em><span style="color:#000000;"><strong>No</strong></span></em></td>`;
                }
                eventHTML +=`<td><input type="button" class="button-12" onclick="store_redirect('${event.eventId}')" value="Update" /></td>
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

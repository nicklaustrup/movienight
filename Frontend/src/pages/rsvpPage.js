import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RSVPClient from "../api/rsvpClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RSVPPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getEvent', 'getRSVP', 'getUser', 'renderEvent', 'renderLogin', 'renderMenu', 'onUpdate'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new RSVPClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        var eventId = window.localStorage.getItem('eventId'); //searches for the eventId in localStorage
        this.getUser(userId);
        this.getEvent(eventId);
        this.getRSVP(userId, eventId);
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderEvent);
        this.dataStore.addChangeListener(this.renderLogin);
        document.getElementById('update-form').addEventListener('submit', this.onUpdate);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderEvent() {
        const event = this.dataStore.get("event");
        const rsvp = this.dataStore.get("rsvp");
        if (event) {
            //Setting the values for all the inputs using the response returned by getEvent
            document.getElementById('userId').value= window.localStorage.getItem('userId');
            document.getElementById('eventId').value= window.localStorage.getItem('eventId');
            document.getElementById('eventTitle').innerHTML= `Title: ${event.eventTitle}`;
            document.getElementById('movieTitle').innerHTML= `Movie: ${event.title}`;
            document.getElementById('movieDescription').innerHTML= `Description:<br>${event.description}`;
            let dateFormatted = new Date(event.date).toLocaleString();
            document.getElementById('date').innerHTML= `Date: ${dateFormatted}`;
            if (event.active) {
                document.getElementById('active').innerHTML= `Active?: Yes`;
            }
            else {
                document.getElementById('active').innerHTML= `Active?: No`;
            }
            if (rsvp.isAttending) {
               document.getElementById('active_round_yes').checked=true;
            }
            else {
                document.getElementById('active_round_no').checked=true;
            };
            let resultRSVP = document.getElementById("RSVP");
            let rsvpHTML = "";
            for (let rsvp of event.users){
                rsvpHTML += `<tr>
                <td>${rsvp.firstName} ${rsvp.lastName}</td>`;
                if (rsvp.attending) {
                    rsvpHTML += `<td><em><span style="color:#fd8d38;"><strong>Yes</strong></span></em></td></tr>`;
                }
                else {
                    rsvpHTML += `<td><em><span style="color:#000000;"><strong>No</strong></span></em></td></td></tr>`;
                }
            }
            resultRSVP.innerHTML = rsvpHTML;
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
        else {
            document.getElementById("menu").innerHTML = `
                      <ul>
                        <li><a href="rsvps.html">RSVP</a></li>
                        <li><a href="index.html" id="login"></a></li>
                      </ul>
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

    async getRSVP(userId, eventId) {
        let result = await this.client.getRSVP(userId, eventId, this.errorHandler);
        this.dataStore.set("rsvp", result);
        if (result) {
            this.showMessage(`Got RSVP!`)
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

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
//        this.dataStore.set("rsvp", null);

        // Gathering the values from all the inputs in the form
        let userId = document.getElementById("userId").value;
        let eventId = document.getElementById("eventId").value;
        var activeRadioButtons = document.getElementsByName('active_round');
        let isAttending = activeRadioButtons[0].checked; //checks the first radio button which is Yes

        console.log(userId);
        console.log(eventId);
        console.log(isAttending);

        //Submits all the information in order to update the record
        const updatedRSVP = await this.client.updateRSVP(userId, eventId, isAttending, this.errorHandler);

        this.dataStore.set("rsvp", updatedRSVP);

        if (updatedRSVP) {
            this.showMessage(`Updated RSVP ${updatedRSVP.eventId}!`)
        } else {
            this.errorHandler("Error updating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const rsvpPage = new RSVPPage();
    await rsvpPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

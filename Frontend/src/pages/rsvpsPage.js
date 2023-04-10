import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RSVPClient from "../api/rsvpsClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RSVPAllPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllRSVPs', 'getUser', 'renderRSVPs', 'renderLogin', 'renderMenu'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new RSVPClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.getAllRSVPs(userId);
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderRSVPs);
        this.dataStore.addChangeListener(this.renderLogin);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderRSVPs() {
        let resultArea = document.getElementById("rsvps-info");
        const rsvps = this.dataStore.get("rsvps");
        let rvspHTML = "";

        if (rsvps) {
            for (let rsvp of rsvps){
                let dateFormatted = new Date(rsvp.date).toLocaleString();
                rvspHTML += `<tr>
                    <td>${dateFormatted}</td>
                    <td>${rsvp.eventTitle}</td>
                    <td>${rsvp.title}</td>`;
                if (rsvp.active) {
                    rvspHTML += `<td><em><span style="color:#00FF00;"><strong>Yes</strong></span></em></td>`;
                    }
                else {
                    rvspHTML += `<td><em><span style="color:#FF0000;"><strong>No</strong></span></em></td>`;
                };
                if (rsvp.isAttending) {
                    rvspHTML += `<td><em><span style="color:#00FF00;"><strong>Yes</strong></span></em></td>`;
                    }
                else {
                    rvspHTML += `<td><em><span style="color:#FF0000;"><strong>No</strong></span></em></td>`;
                };
                if (rsvp.active) {
                    rvspHTML +=`<td><input type="button" onclick="store_redirect('${rsvp.eventId}')" value="Update" /></td>
                    </tr>`;
                    }
                else {
                    rvspHTML += `<td></td>
                    </tr>`;
                }
            }
            resultArea.innerHTML = rvspHTML;
        } else {
            resultArea.innerHTML = "No RSVPs";
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

    async getAllRSVPs(userId) {
        let result = await this.client.getAllRSVPs(userId, this.errorHandler);
        this.dataStore.set("rsvps", result);
        if (result) {
            this.showMessage(`Got All RSVPs!`)
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
    const rsvpsPage = new RSVPAllPage();
    await rsvpsPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

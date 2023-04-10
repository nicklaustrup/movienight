import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/usercreateClient";


class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getUser','renderLogin', 'onCreate', 'renderMenu'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new UserClient();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.dataStore.addChangeListener(this.renderMenu);
        this.dataStore.addChangeListener(this.renderLogin);
        document.getElementById('create-user-form').addEventListener('submit', this.onCreate);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

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

    async getUser(userId) {
        let result = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", result);
        if (result) {
            this.showMessage(`Got User!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("userCreated", null);

        let firstName = document.getElementById("create-firstName-field").value;
        let lastName = document.getElementById("create-lastName-field").value;


        const createdUser = await this.client.createUser(firstName, lastName, this.errorHandler);
        this.dataStore.set("userCreated", createdUser);

        if (createdUser) {
            this.showMessage(`Created ${createdUser.firstName}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userPage = new UserPage();
    userPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

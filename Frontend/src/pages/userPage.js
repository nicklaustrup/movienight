import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/userClient";


class UserPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllUsers', 'onCreate', 'getUser','renderGetUser', 'renderUsers'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-userId-form').addEventListener('submit', this.getUser);
        document.getElementById('create-user-form').addEventListener('submit', this.onCreate);
        this.client = new UserClient();
        this.getAllUsers();
        this.dataStore.addChangeListener(this.renderUsers);

    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderUsers() {
        let resultArea = document.getElementById("user-result");
        let userHTML = "<ul>"

        const users = this.dataStore.get("users");


        if (users) {
            for (let user of users){
            userHTML += `<li>
                <p>${user.firstName} ${user.lastName}</p>
                </li>`
                }
            userHTML += "</ul>";
            resultArea.innerHTML = userHTML;
        } else {
            resultArea.innerHTML = "No Users";
        }
    }

    async renderGetUser() {
        let resultArea = document.getElementById("get-user-result");
        let userHTML = "";

        const user = this.dataStore.get("user");


        if (user) {
            userHTML = `
                <p>${user.firstName} ${user.lastName}</p>
                `;
        userHTML += "</ul>";
        resultArea.innerHTML = userHTML;
    }
         else {
            resultArea.innerHTML = "User Not Found";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async getAllUsers() {
        let result = await this.client.getAllUsers(this.errorHandler);
        this.dataStore.set("users", result);
        if (result) {
            this.showMessage(`Got All Users!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async getUser(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userId = document.getElementById("userId-field").value;
        this.dataStore.set("user", null);

        let result = await this.client.getUser(userId, this.errorHandler);
        this.dataStore.set("user", result);

        if (result) {
            this.showMessage(`Got ${result.firstName}!`)
            this.renderGetUser();
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("user", null);

        let firstName = document.getElementById("create-firstName-field").value;
        let lastName = document.getElementById("create-lastName-field").value;


        const createdUser = await this.client.createUser(firstName, lastName, this.errorHandler);
        this.dataStore.set("user", createdUser);

        if (createdUser) {
            this.showMessage(`Created ${createdUser.firstName}!`)
            this.getAllUsers();
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

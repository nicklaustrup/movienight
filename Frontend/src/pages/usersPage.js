import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UsersClient from "../api/usersClient";


class UserAllPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['getAllUsers', 'getUser','renderUsers', 'renderLogin'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new UsersClient();
        this.getAllUsers();
        var userId = window.localStorage.getItem('userId'); //searches for the userId in localStorage
        this.getUser(userId);
        this.dataStore.addChangeListener(this.renderUsers);
        this.dataStore.addChangeListener(this.renderLogin)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderUsers() {
        let resultArea = document.getElementById("users-info");
        const users = this.dataStore.get("users");
        let userHTML = "";

        if (users) {
            for (let user of users){
            userHTML += `<tr>
                            <td>${user.userId}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                         </td>`;
             }
            resultArea.innerHTML = userHTML;
        } else {
            resultArea.innerHTML = "No Users";
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

    async getAllUsers() {
        let result = await this.client.getAllUsers(this.errorHandler);
        this.dataStore.set("users", result);
        if (result) {
            this.showMessage(`Got All Users!`)
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
    const userAllPage = new UserAllPage();
    userAllPage.mount();
};

window.addEventListener('DOMContentLoaded', main);

import BaseClass from "../util/baseClass";
import axios from 'axios'


export default class MoviesClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getAllMovies', 'getUser'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the user for the given ID.
     * @param userId Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user
     */
    async getUser(userId, errorCallback) {
        try {
            const response = await this.client.get(`/user/${userId}`);
            return response.data;
        } catch (error) {
            this.handleError("getUser", error, errorCallback)
        }
    }
    async getAllMovies(errorCallback) {
        try {
            const response = await this.client.get(`/movie/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllMovies", error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}

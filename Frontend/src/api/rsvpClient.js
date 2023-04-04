import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class RSVPClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getRSVP', 'getAllEventRSVPs', 'getAllUserRSVPs'];
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
     * Gets the rsvp for the given ID.
     * @param userId Unique identifier for users.
     * @param eventId Unique identifier for events.
     * Together these will be the primary key for the rsvp table.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The rsvp object.
     */
    async getRSVP(userId, eventId, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/${userId}_${eventId}`);
            return response.data;
        } catch (error) {
            this.handleError("getRSVP", error, errorCallback)
        }
    }
    async getAllEventRSVPs(eventId, errorCallback) {
        try {
            const response = await this.client.get(`/event/${eventId}`);
            // const response = await this.client.get(`/rsvp/users/${eventId}`);
            return response.data;
        } catch (error) {
            this.handleError("getAllEventRSVPs", error, errorCallback)
        }
    }
    async getAllUserRSVPs(userId, errorCallback) {
        try {
            const response = await this.client.get(`/rsvp/events/${userId}`);
            return response.data;
        } catch (error) {
            this.handleError("getAllEventRSVPs", error, errorCallback)
        }
    }

    async updateMovie(title, description, errorCallback) {
        try {
            const response = await this.client.post(`/movie`, {
                title: title,
                description: description
            });
            return response.data;
        } catch (error) {
            this.handleError("createMovie", error, errorCallback);
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

import apiInstance from "./ApiInstance";

// const debug = true;

let api;
export default class UserAuthApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }
    }
}
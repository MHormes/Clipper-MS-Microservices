import apiInstance from "./ApiInstance";
import type {IUserLoginDetails} from "../model/UserModel";

// const debug = true;

let api;
// let prefix = "/collection/api"
export default class UserAuthApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }
    }
}
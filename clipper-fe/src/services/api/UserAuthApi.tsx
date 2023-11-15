import apiInstance from "./ApiInstance";
import type {IUserLoginDetails} from "../model/UserModel";

// const debug = true;

let api;
// let prefix = "/collection/api"
export default class UserAuthApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');
        }
    }

    loginUser = async (userDetails: IUserLoginDetails) => {
        return process.env.REACT_APP_ACCESS_TOKEN_DEV;
        // USE THIS FOR PRODUCTION;
        // return api
        //     .post(prefix + "/login", userDetails)
        //     .then((response: AxiosResponse<string>) => {
        //         if (debug) console.log(response.data);
        //         if (response.status === 200) {
        //             return response;
        //         } else {
        //             console.log("Response status is not 200");
        //             console.log(response);
        //         }
        //     })
        //     .catch((error) => {
        //         console.log(error);
        //         return null;
        //     });
    }

}
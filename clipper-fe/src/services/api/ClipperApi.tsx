import {AxiosResponse} from "axios";
import apiInstance from "./ApiInstance";
import type {IClipper, IClipperCreateRequest} from "../model/ClipperModel";
import KeycloakService from "../security/keycloak";

const debug = false;

let api;
let prefix = "/collection/api"
export default class ClipperApi {

    constructor() {
        api = apiInstance.init();
    }

    getAllClippers = async () => {
        //todo - create middleware to remove auth addition on each call
        let token = KeycloakService.GetAccessToken();
        if(token){
            console.log(token);
            api.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()}`;
            console.log(api.defaults.headers.common["Authorization"])
        }

        return api
            .get(prefix + "/clipper/all")
            .then((response: AxiosResponse<IClipper[]>) => {
                if(debug) console.log(response.data);
                console.log(response.headers)
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error)
                console.log(error.message);
                return null;
            });
    }

    getClipperWithId = async (clipperId) => {
        //todo - create middleware to remove auth addition on each call
        if(KeycloakService.GetAccessToken()){
            api.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()} `;
        }

        return api
            .get(prefix + "/clipper/" + clipperId)
            .then((response: AxiosResponse<IClipper>) => {
                if(debug) console.log(response.data);
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }

    createClipper = async (clipperObject: IClipperCreateRequest, image: File) => {
        //todo - create middleware to remove auth addition on each call
        if(KeycloakService.GetAccessToken()){
            api.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()} `;
        }

        const createJson = JSON.stringify(clipperObject);
        const clipperBlob = new Blob([createJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('clipper', clipperBlob);

        console.log(formData);
        return api
            .post(prefix + "/clipper/add", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data ',
                }
            })
            .then((response: AxiosResponse<IClipper>) => {
                if(debug) console.log(response.data);
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }

    updateClipper = async (clipperObject: IClipperCreateRequest, image: File) => {
        //todo - create middleware to remove auth addition on each call
        if(KeycloakService.GetAccessToken()){
            api.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()} `;
        }

        const updateJson = JSON.stringify(clipperObject);
        const clipperBlob = new Blob([updateJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('clipper', clipperBlob);

        console.log(formData);
        return api
            .put(prefix + `/clipper/update/${clipperObject.id}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data ',
                }
            })
            .then((response: AxiosResponse<IClipper>) => {
                if(debug) console.log(response.data);
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }

    deleteClipper = async (clipperId: string) => {
        //todo - create middleware to remove auth addition on each call
        if(KeycloakService.GetAccessToken()){
            api.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()} `;
        }

        return api
            .delete(prefix + "/clipper/delete/" + clipperId)
            .then((response) => {
                if(debug) console.log(response);
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }
}
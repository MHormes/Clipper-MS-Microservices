import {AxiosResponse} from "axios";
import apiInstance from "./ApiInstance";
import type {IClipper, IClipperCreateRequest} from "../model/ClipperModel";

const debug = false;

let api;
export default class ClipperApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');
        }
    }
    getAllClippers = async () => {
        return api
            .get("/clipper/all")
            .then((response: AxiosResponse<IClipper[]>) => {
                if(debug) console.log(response.data);
                if(response.status === 200){
                    return response;
                }
            })
            .catch((error) => {
                console.log(error.message);
                return null;
            });
    }

    getClipperWithId = async (clipperId) => {
        return api
            .get("/clipper/" + clipperId)
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

    }

    deleteClipper = async (clipperId) => {
        return api
            .delete("/clipper/delete/" + clipperId)
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
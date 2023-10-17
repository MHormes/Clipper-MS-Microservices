import {AxiosResponse} from "axios";
import apiInstance from "./ApiInstance";
import type {IClipper, IClipperCreateRequest} from "../model/ClipperModel";
import type {ISeries} from "../model/SeriesModel";

const debug = false;

let api;
let prefix = "/collection/api"
export default class ClipperApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');
        }
    }
    getAllClippers = async () => {
        return api
            .get(prefix + "/clipper/all")
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
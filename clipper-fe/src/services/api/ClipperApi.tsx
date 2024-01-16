import {AxiosInstance, AxiosResponse} from "axios";
import apiInstance from "./ApiInstance";
import type {IClipper, IClipperCreateRequest} from "../model/ClipperModel";

const debug = false;

let api: AxiosInstance;
let prefix = "/collection/api"
export default class ClipperApi {

    constructor() {
        api = apiInstance.init("8071");
    }

    getAllClippers = async () => {
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

    getClipperWithId = async (clipperId: string) => {
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
            .put(prefix + `/clipper/update`, formData, {
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
import {AxiosResponse} from "axios";
import apiInstance from "./apiInstance";
import type {IClipper} from "../model/ClipperModel";

const debug = true;

let api;
export default class ClipperApi {

    constructor() {
        api = apiInstance.init();
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
                console.log(error);
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
}
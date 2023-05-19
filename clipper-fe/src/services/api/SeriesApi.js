import apiInstance from "./ApiInstance";
import {AxiosResponse} from "axios";
import type {ISeries} from "../model/SeriesModel";

const debug = false;

let api;
export default class SeriesApi {

    constructor() {
        api = apiInstance.init();
        if(localStorage.getItem('token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');
        }
    }

    getSeriesWithId = async (seriesId) => {
        return api
            .get("/series/" + seriesId)
            .then((response: AxiosResponse<ISeries>) => {
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

    getAllSeries = async () => {
        return api
            .get("/series/all")
            .then((response: AxiosResponse<ISeries[]>) => {
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
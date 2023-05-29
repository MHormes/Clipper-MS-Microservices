import apiInstance from "./ApiInstance";
import {AxiosResponse} from "axios";
import type {ISeries, ISeriesCreateRequest} from "../model/SeriesModel";

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
                console.log(error.message);
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

    getAvailableSeriesNumbers = async (seriesId: string) => {
        return api
            .get(`/series/${seriesId}/available`)
            .then((response: AxiosResponse<number[]>) => {
                if(debug) console.log(response.data);
                if(response.status === 200){
                    return response.data;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }

    createSeries = async (seriesObject: ISeriesCreateRequest, image: File) => {
        const seriesJson = JSON.stringify(seriesObject);
        const seriesBlob = new Blob([seriesJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('series', seriesBlob);

        console.log(formData);
        return api
            .post("/series/add", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data ',
                }
            })
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

    deleteSeries = async (seriesId: string) => {
        return api
            .delete("/series/delete/" + seriesId)
            .then((response) => {
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
import apiInstance from "./ApiInstance";
import {AxiosResponse} from "axios";
import type {ISeries, ISeriesCreateRequest} from "../model/SeriesModel";

const debug = false;

let api;
let prefix = "/collection/api"
export default class SeriesApi {

    constructor() {
        api = apiInstance.init();
    }

    getSeriesWithId = async (seriesId) => {
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        return api
            .get(prefix + "/series/" + seriesId)
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
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        return api
            .get(prefix + "/series/all")
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
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        return api
            .get(prefix + `/series/${seriesId}/available`)
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
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        const seriesJson = JSON.stringify(seriesObject);
        const seriesBlob = new Blob([seriesJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('series', seriesBlob);

        console.log(formData);
        return api
            .post(prefix + "/series/add", formData, {
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

    updateSeries = async (seriesObject: ISeriesCreateRequest, image: File) => {
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        const seriesJson = JSON.stringify(seriesObject);
        const seriesBlob = new Blob([seriesJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('series', seriesBlob);

        console.log(formData);
        return api
            .put(prefix + `/series/update/${seriesObject.id}`, formData, {
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
        //todo - create middleware to remove auth addition on each call
        if(localStorage.getItem('access_token')){
            api.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('access_token');
        }

        return api
            .delete(prefix + "/series/delete/" + seriesId)
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
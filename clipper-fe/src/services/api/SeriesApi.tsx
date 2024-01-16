import apiInstance from "./ApiInstance";
import {AxiosInstance, AxiosResponse} from "axios";
import type {ISeries, ISeriesCreateRequest} from "../model/SeriesModel";

const debug = false;

let api: AxiosInstance;
let prefix = "/collection/api"
export default class SeriesApi {

    constructor() {
        api = apiInstance.init("8071");
    }

    getSeriesWithId = async (seriesId: string) => {
        return api
            .get(prefix + "/series/" + seriesId)
            .then((response: AxiosResponse<ISeries>) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
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
            .get(prefix + "/series/all")
            .then((response: AxiosResponse<ISeries[]>) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
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
            .get(prefix + `/series/${seriesId}/available`)
            .then((response: AxiosResponse<number[]>) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
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
            .post(prefix + "/series/add", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data ',
                }
            })
            .then((response: AxiosResponse<ISeries>) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }

    updateSeries = async (seriesObject: ISeriesCreateRequest, image: File) => {
        const seriesJson = JSON.stringify(seriesObject);
        const seriesBlob = new Blob([seriesJson], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', image);
        formData.append('series', seriesBlob);

        console.log(formData);
        return api
            .put(prefix + `/series/update`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data ',
                }
            })
            .then((response: AxiosResponse<ISeries>) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
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
            .delete(prefix + "/series/delete/" + seriesId)
            .then((response) => {
                if (debug) console.log(response.data);
                if (response.status === 200) {
                    return response;
                }
            })
            .catch((error) => {
                console.log(error);
                return null;
            });
    }
}
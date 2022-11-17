import axios from "axios";
import apiInstance from "./apiInstance";

const debug = true;

let api;
export default class SeriesApi {

    constructor() {
        api = apiInstance.init();
    }

    getSeriesWithId = async (seriesId) => {
        console.log("getSeriesWithId", api.url);
        return api
            .get("/series/" + seriesId)
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
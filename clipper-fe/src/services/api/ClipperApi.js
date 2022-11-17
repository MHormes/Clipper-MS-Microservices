import axios from "axios";

const debug = false;
export default class ClipperApi {

    constructor() {
        this.apiToken = null;
        this.axiosInstance = null;
        this.apiUrl = process.env.REACT_APP_API_BASE_URL;
    }

    init = () => {
        this.apiToken = process.env.REACT_APP_ACCESS_TOKEN_DEV;
        let headers = {
            Accept: "application/json"
        };
        if (this.apiToken) {
            headers.Authorization = `Bearer ${
                this.apiToken
            }`;
        }
        this.axiosInstance = axios.create({baseURL: this.apiUrl});
        this.axiosInstance.headers = {
            Authorization: `Bearer ${this.apiToken}`
        };
        return this.axiosInstance;
    };

    getAllClippers = async () => {
        return this.init()
            .get("/clipper/all")
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
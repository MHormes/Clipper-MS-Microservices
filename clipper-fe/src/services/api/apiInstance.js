import axios, {AxiosInstance} from "axios";

const debug = false;
export default class apiInstance {


    static init() {
        let apiToken = process.env.REACT_APP_ACCESS_TOKEN_DEV;
        let headers = {
            Accept: "application/json"
        };
        if (apiToken) {
            headers.Authorization = `Bearer ${
                apiToken
            }`;
        }
        let axiosInstance: AxiosInstance<AxiosInstance> = axios.create({baseURL: process.env.REACT_APP_API_BASE_URL});
        axiosInstance.headers = {
            Authorization: `Bearer ${apiToken}`
        };
        return axiosInstance;
    }
}
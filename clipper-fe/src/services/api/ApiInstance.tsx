import axios, {AxiosInstance} from "axios";

const debug = false;
export default class ApiInstance {

    static init() {
        let axiosInstance: AxiosInstance = axios.create({baseURL: process.env.REACT_APP_API_BASE_URL });
        axiosInstance.defaults.headers.common = {
            Accept: "application/json"
        };
        if (debug) console.log("axiosInstance", axiosInstance.defaults.headers.common);
        return axiosInstance;
    }
}
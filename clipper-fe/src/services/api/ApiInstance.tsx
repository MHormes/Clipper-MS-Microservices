import axios, {AxiosInstance} from "axios";
import Middleware from "../Middleware";

const debug = false;
export default class ApiInstance {

    static init(port: string) {
        let baseUrl = process.env.REACT_APP_API_BASE_URL + port;
        let axiosInstance: AxiosInstance = axios.create({baseURL: baseUrl });
        // let axiosInstance: AxiosInstance = axios.create({baseURL: process.env.REACT_APP_API_BASE_URL });

        new Middleware(axiosInstance);

        axiosInstance.defaults.headers.common = {
            Accept: "application/json"
        };
        if (debug) console.log("axiosInstance", axiosInstance.defaults.headers.common);
        return axiosInstance;
    }
}
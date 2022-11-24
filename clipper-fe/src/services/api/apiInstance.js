import axios, {AxiosInstance} from "axios";

const debug = false;
export default class apiInstance {


    static init() {
        let apiToken = process.env.REACT_APP_ACCESS_TOKEN_DEV;
        let axiosInstance: AxiosInstance<AxiosInstance> = axios.create({baseURL: process.env.REACT_APP_API_BASE_URL});
        axiosInstance.defaults.headers.common = {
            Accept: "application/json",
            Authorization: "Bearer " + apiToken
        };
        if (debug) console.log("axiosInstance", axiosInstance.defaults.headers.common);
        return axiosInstance;
    }
}
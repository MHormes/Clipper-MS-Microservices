import axios, {AxiosInstance} from "axios";

const debug = false;
export default class ApiInstance {

    static init() {
        //Uncomment below to override api access token
        // let apiToken = process.env.REACT_APP_ACCESS_TOKEN_DEV;
        // localStorage.setItem('accessToken', apiToken);
        let axiosInstance: AxiosInstance<AxiosInstance> = axios.create({baseURL: process.env.REACT_APP_API_BASE_URL});
        axiosInstance.defaults.headers.common = {
            Accept: "application/json"
        };
        if (debug) console.log("axiosInstance", axiosInstance.defaults.headers.common);
        return axiosInstance;
    }
}
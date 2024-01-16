import axios, {AxiosInstance} from "axios";
import KeycloakService from "./security/keycloak";

export default class Middleware {
    requestSender: AxiosInstance

    constructor(axios: AxiosInstance) {
        this.requestSender = axios

        this.setBearerTokenInterceptor()
        this.authorizationErrorInterceptor()
    }

    log(message: string) {
        const debug = false; // This should probably be set as a class property or external configuration
        if (debug) {
            console.log(`[Flowcontrol]_[Middleware] - ${message} `);
        }
    }

    // Intercepts axios requests and adds auth headers before request is send
    setBearerTokenInterceptor() {
        this.requestSender.interceptors.request.use(
            async (config) => {
                if (!KeycloakService.IsLoggedIn()) {
                    this.log("Failed: authToken is null");
                    return config;
                }

                this.log("Success: authToken is set");

                config.headers.Authorization = `Bearer ${KeycloakService.GetAccessToken()}`;
                config.headers.Accept = "application/json";

                this.log(`Request headers are set: ${JSON.stringify(config.headers)}`);
                this.log("Ready to send request..");

                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );
    }


    isErrorResponseForRetry(status: number) {
        return [401, 403].includes(status);
    }

    isErrorResponseToNotify(status: number) {
        return [400, 500, 408].includes(status);
    }

    //Checks if request has failed when failed it refreshes the auth token
    authorizationErrorInterceptor() {
        this.requestSender.interceptors.response.use(
            (response) => {
                this.log("Request success..")
                return response;
            },
            async (error) => {
                this.log(`Request error: ${error}`);

                const originalRequest = error.config;
                const responseStatus = error.response.status;

                if (this.isErrorResponseForRetry(responseStatus) && !originalRequest._retry) {
                    this.log(`Unauthorized or Forbidden response status ${responseStatus}: ${error.message}`);

                    originalRequest._retry = true;

                    try {
                        const callBack = () => {
                            this.log("Token refreshed")
                        }
                        await KeycloakService.UpdateToken(callBack);
                    } catch (e) {
                        this.log("Token refresh failed, not retrying the request");
                        return Promise.reject(error);
                    }

                    axios.defaults.headers.common["Authorization"] = `Bearer ${KeycloakService.GetAccessToken()} `;

                    this.log("Retry original request..");

                    return this.requestSender(originalRequest);
                }

                if (this.isErrorResponseToNotify(responseStatus)) {
                    this.log(`Notify due to response status ${responseStatus}: ${error.message}`);
                }

                return Promise.reject(error);
            }
        );
    }
}
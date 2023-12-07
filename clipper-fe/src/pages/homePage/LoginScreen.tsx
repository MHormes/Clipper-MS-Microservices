import {useLocation, useNavigate} from "react-router";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../../services/security/RouteAuthProvider";
import CardTitle from "../../components/card/CardTitle";
import CardButton from "../../components/card/CardButton";
import CardTextBox from "../../components/card/CardTextBox";
import {AxiosResponse} from "axios";
import apiInstance from "../../services/api/ApiInstance";


const LoginScreen = () => {

    const navigate = useNavigate();
    const location = useLocation();

    const { isAuthenticated, login } = useContext(AuthContext);

    useEffect(() => {
        console.log("LoginScreen Component Mounted");

        //WALKING SKELETON TEST CALL - REMOVE LATER
        const api = apiInstance.init();
        //Request message endpoint so send a message on the message bus
        // this message is returned with the api call and displayed on the screen
        api
            .get("/collection/api/test/message")
            .then(async (response: AxiosResponse) => {
                console.log("From coll: " + response.data);
                if (response.status === 200) {
                    setCollectionCall(response.data);
                    // Flag is set, now initiate the second API call
                    return api.get("/trading/api/test/message");
                }
                return Promise.reject("Failed to fetch from collection API");
            })
            .then((response: AxiosResponse) => {
                console.log("From trad: " + response.data);
                if (response.status === 200) {
                    setTradingCall(response.data);
                }
            })
            .catch((error) => {
                console.log(error.message);
            });

        // Redirect if already authenticated
        if (isAuthenticated) {
            console.log("Already authenticated, redirecting...");
            const origin = location.state?.from?.pathname || '/Clippers';
            navigate(origin);
        }
    }, [navigate, isAuthenticated, location.state?.from?.pathname]);

    const handleLogin = () => {
        login();
    };

    //VALUES FOR WALKING SKELETON TEST
    const [collectionCall, setCollectionCall] = useState<string>("");
    const [tradingCall, setTradingCall] = useState<string>("");

    return (
        <div className="card card-side bg-base-300 m-3">
            <div className="card-body items-center text-center">
                <CardTitle cardTitle={"Login"} centered={true}/>
                <CardButton
                    buttonAction={() => handleLogin()}
                    buttonText={"Login"}
                />

                <h1>Collection message: {collectionCall}</h1>
                <h1>Trading response: {tradingCall}</h1>
            </div>
        </div>
    )
}

export default LoginScreen;
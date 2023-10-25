import {useLocation, useNavigate} from "react-router";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../../services/RouteAuthProvider";
import CardTitle from "../../components/card/CardTitle";
import CardButton from "../../components/card/CardButton";
import CardTextBox from "../../components/card/CardTextBox";
import {AxiosResponse} from "axios";
import apiInstance from "../../services/api/ApiInstance";


const LoginScreen = () => {

    const navigate = useNavigate();
    const location = useLocation();

    const {tokenValue} = useContext(AuthContext);
    const {onLogin} = useContext(AuthContext);

    const [loginDetails, setLoginDetails] = useState({
        username: "",
        password: ""
    })

    const onChange = e => {
        setLoginDetails({
            ...loginDetails,
            [e.target.name]: e.target.value
        });
    }

    //VALUES FOR WALKING SKELETON TEST
    const [collectionCall, setCollectionCall] = useState<string>("");
    const [tradingCall, setTradingCall] = useState<string>("");

    useEffect(() => {
        //WALKING SKELETON TEST CALL - REMOVE LATER
        const api = apiInstance.init();
        //Request kafka endpoint so send a message on the message bus
        // this message is returned with the api call and displayed on the screen
        api
            .get("/collection/api/test/kafka")
            .then(async (response: AxiosResponse) => {
                console.log(response.data);
                if (response.status === 200) {
                    setCollectionCall(response.data);
                    //Call the trading module and check the kafka message bus for any messages
                    // api call returns the message which will be displayed on the screen
                    await tradingCall();
                }
            })
            .catch((error) => {
                console.log(error.message);
            });

        async function tradingCall() {
            api
                .get("/trading/api/test/kafka")
                .then((response: AxiosResponse) => {
                    console.log(response.data);
                    if(response.status === 200){
                        setTradingCall(response.data);
                    }
                })
                .catch((error) => {
                    console.log(error.message);
                });
        }


        //todo check token validity
        if (tokenValue) {
            console.log("Token found, redirecting....");
            const origin = location.state?.from?.pathname || '/Clippers';
            navigate(origin);
        }
    }, [navigate]);

    async function handleLogin() {
        let data = {
            username: loginDetails.username,
            password: loginDetails.password
        }
        await onLogin(data);
    }

    return (
        <div className="card card-side bg-base-300 m-3">
            <div className="card-body items-center text-center">
                <CardTitle cardTitle={"Login"} centered={true}/>
                <CardTextBox
                    boxHint={"Username"}
                    boxLabel={"Username"}
                    onChange={onChange}
                    value={loginDetails.username}
                    fieldName={"username"}
                />
                <CardTextBox
                    boxHint={"Password"}
                    boxLabel={"Password"}
                    onChange={onChange}
                    value={loginDetails.password}
                    fieldName={"password"}
                    type={"password"}/>
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
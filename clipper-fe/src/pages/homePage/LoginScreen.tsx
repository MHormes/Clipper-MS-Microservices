import {useLocation, useNavigate} from "react-router";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../../services/RouteAuthProvider";
import CardTitle from "../../components/card/CardTitle";
import CardButton from "../../components/card/CardButton";
import CardTextBox from "../../components/card/CardTextBox";


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

    useEffect(() => {
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
            </div>
        </div>
    )
}

export default LoginScreen;
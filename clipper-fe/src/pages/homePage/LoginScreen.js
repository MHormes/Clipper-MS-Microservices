import {useLocation, useNavigate} from "react-router";
import React, {useContext, useEffect} from "react";
import {AuthContext} from "../../services/RouteAuthProvider";
import CardTitle from "../../components/card/CardTitle";
import CardButton from "../../components/card/CardButton";
import CardTextBox from "../../components/card/CardTextBox";


const LoginScreen = () => {

    const navigate = useNavigate();
    const location = useLocation();

    const {tokenValue} = useContext(AuthContext);
    const {onLogin} = useContext(AuthContext);

    useEffect(() => {
        //todo check token validity
        if (tokenValue) {
            console.log("Token found, redirecting....");
            const origin = location.state?.from?.pathname || '/Clippers';
            navigate(origin);
        }
    }, [navigate]);

    return (
        <div className="card card-side bg-base-300 m-3">
            <div className="card-body items-center text-center">
                <CardTitle cardTitle={"Login"} centered={true}/>
                <CardTextBox boxHint={"Username"} boxLabel={"Username"}/>
                <CardTextBox boxHint={"Password"} boxLabel={"Password"}/>
                <CardButton className="btn btn-primary btn-xl" buttonAction={onLogin} buttonText={"Login"}>Login</CardButton>
            </div>
        </div>
    )
}

export default LoginScreen;
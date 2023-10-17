import UserAuthApi from "./api/UserAuthApi";
import React, {createContext, useEffect, useMemo, useState} from "react";
import {useLocation, useNavigate} from "react-router";
import type {IUserLoginDetails} from "./model/UserModel";

export const AuthContext = createContext(null);

const userAuthApi = new UserAuthApi();

const debug = true;
const RouteAuthProvider = ({children}) => {

    const navigate = useNavigate();
    const location = useLocation();

    const [token, setToken] = useState("" as string);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setToken(token);
        }
    }, []);

    const handleLogin = async (authDetails: IUserLoginDetails) => {
        console.log("Logging in")
        const token = await userAuthApi.loginUser(authDetails);

        token != null ? setToken(token) : alert("Invalid username or password");
        if (token) {
            localStorage.setItem("token", token);
            if (debug) console.log("Token has been set", token);
            const origin = location.state?.from?.pathname || '/Clippers';
            navigate(origin);
        }
    };

    const handleLogout = () => {
        setToken(null);
        localStorage.removeItem("token");
    };


    const value = useMemo(() => ({
        tokenValue: token,
        onLogin: handleLogin,
        onLogout: handleLogout,
    }), [token]);


    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default RouteAuthProvider;
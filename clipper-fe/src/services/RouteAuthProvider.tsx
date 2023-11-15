import UserAuthApi from "./api/UserAuthApi";
import React, {createContext, useCallback, useEffect, useMemo, useState} from "react";
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
        console.log("RouteAuthProvider Mounted/Updated");
        const token = localStorage.getItem("token");
        console.log("Token state: ", token);
        if (token) {
            setToken(token);
        }
    }, []);

    const handleLogin = useCallback(async (authDetails: IUserLoginDetails) => {
        console.log("Logging in");
        const token = await userAuthApi.loginUser(authDetails);

        if (token != null) {
            setToken(token);
            localStorage.setItem("token", token);
            if (debug) console.log("Token has been set", token);
            const origin = location.state?.from?.pathname || '/Clippers';
            navigate(origin);
        } else {
            alert("Invalid username or password");
        }
    }, [setToken, location.state, navigate]);


    const handleLogout = useCallback(() => {
        setToken(null);
        localStorage.removeItem("token");
    }, [setToken]);

    const value = useMemo(() => ({
        tokenValue: token,
        onLogin: handleLogin,
        onLogout: handleLogout,
    }), [token, handleLogout, handleLogin]);


    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default RouteAuthProvider;
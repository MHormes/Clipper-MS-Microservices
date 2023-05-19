import UserAuthApi from "./api/UserAuthApi";
import {createContext, useEffect, useMemo, useState} from "react";
import {useLocation, useNavigate} from "react-router";

export const AuthContext = createContext(null);

const userAuthApi = new UserAuthApi();

const debug = false;
const RouteAuthProvider = ({children}) => {

    const navigate = useNavigate();
    const location = useLocation();

    const [token, setToken] = useState();

    useEffect( () => {
        const token = localStorage.getItem("token");
        if (token) {
            setToken(token);
        }
    }, []);

    const handleLogin = async () => {
        console.log("Logging in")
        const token = await userAuthApi.loginUser({username: "test", password: "test"});

        setToken(token);
        if (debug) console.log("Token has been set", token);
        if (token) {
            localStorage.setItem("token", token);
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
    }));

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default RouteAuthProvider;
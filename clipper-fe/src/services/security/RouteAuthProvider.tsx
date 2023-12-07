import React, { createContext, useEffect, useState } from 'react';
import keycloak from './keycloak';
import KeycloakService from "./keycloak"; // Import the Keycloak instance

export const AuthContext = createContext({
    isAuthenticated: false,
    login: () => {},
    logout: () => {}
});

const RouteAuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        KeycloakService.GetAccessToken() ? setIsAuthenticated(true) : setIsAuthenticated(false);
    }, []);

    const login = async () => {
        await KeycloakService.CallLogin();
    }
    const logout = () => keycloak.CallLogOut();

    const contextValue = {
        isAuthenticated,
        login,
        logout
    };

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export default RouteAuthProvider;

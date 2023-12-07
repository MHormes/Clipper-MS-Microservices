import React, { useEffect, useState } from 'react';
import KeycloakService from './keycloak';

const debug = false;
const KeycloakInit = ({ children }) => {
    const [keycloakInitialized, setKeycloakInitialized] = useState(false);

    useEffect(() => {
        KeycloakService.CallInit()
            .then(() => {
                setKeycloakInitialized(true);
                if(KeycloakService.IsLoggedIn()){
                    if (debug) console.log("KeycloakInit: Logged in, setting tokens")
                    localStorage.setItem("access_token", KeycloakService.GetAccessToken());
                    if (debug) console.log(KeycloakService.GetAccessToken());
                    localStorage.setItem("refresh_token", KeycloakService.GetRefreshToken());
                    if (debug) console.log(KeycloakService.GetRefreshToken());
                }
            })
            .catch(error => {
                console.error('Keycloak initialization error:', error);
            });
    }, []);

    if (!keycloakInitialized) {
        // Optionally render a loading spinner or a placeholder
        return <div>Loading...</div>;
    }

    return children;
};

export default KeycloakInit;

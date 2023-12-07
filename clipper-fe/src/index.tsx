import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter} from 'react-router-dom';
import './index.css';
import MainContainer from "./MainContainer";
import KeycloakInit from "./services/security/KeycloakInit";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <KeycloakInit>
            <MainContainer/>
        </KeycloakInit>
    </BrowserRouter>
);
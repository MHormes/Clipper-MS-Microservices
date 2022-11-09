import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter, Route, Routes    } from 'react-router-dom';
import './index.css';
import MainContainer from "./components/MainContainer";
import ClipperList from "./components/clipper/ClipperList";

const accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNSG9ybWVzIiwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2OTk1MzM4NTJ9.L99RQJGEk68pKC-wfonr7vdezlSb0zCtmUdIhZh2Xws"

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainContainer props={accessToken}/>}/>
                <Route path="/clipperList" element={<ClipperList/>}/>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
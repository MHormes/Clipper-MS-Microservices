import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import './index.css';
import MainContainer from "./components/MainContainer";
import ClipperList from "./components/clipper/viewClipper/ClipperList";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/home" element={
                    <MainContainer
                    />
                }/>
                <Route path="/" element={
                    <ClipperList
                    />
                }/>
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './index.css';
import MainContainer from "./components/MainContainer";
import ClipperList from "./components/clipper/viewClipper/ClipperList";
import SeriesWithId from './components/series/viewSeries/SeriesWithId';

//todo refactor
const seriesId="962b7877-0d71-4e7d-bb34-24a12dd4617d";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/home" element={
                    <MainContainer
                    />
                } />
                <Route path="/" element={
                    <ClipperList
                    />
                } />
                <Route path="/seriesId" element={
                    <SeriesWithId
                    seriesIdProps={seriesId}
                    />}
                />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
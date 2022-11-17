import React from "react";
import {Route, Routes} from "react-router-dom";
import ClipperList from "./clipper/viewClipper/ClipperList";
import SeriesWithId from "./series/viewSeries/SeriesWithId";
import LoginScreen from "./homePage/LoginScreen";
import Navbar from "./siteDefaults/Navbar";

//todo refactor
const seriesId="962b7877-0d71-4e7d-bb34-24a12dd4617d";

const MainContainer = () => {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={
                    <LoginScreen
                    />
                } />
                <Route path="/clippers" element={
                    <ClipperList
                    />
                } />
                <Route path="/series" element={
                    <SeriesWithId
                        seriesIdProps={seriesId}
                    />}
                />
            </Routes>
        </>
    )
}

export default MainContainer;
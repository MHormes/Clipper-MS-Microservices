import React from "react";
import {Route, Routes} from "react-router-dom";
import ClipperList from "./clipper/viewClipper/ClipperList";
import SeriesWithId from "./series/viewSeries/SeriesWithId";
import LoginScreen from "./homePage/LoginScreen";
import Navbar from "./siteDefaults/Navbar";
import AllClippers from "./clipper/allClippers/AllClippers";
import AllSeries from "./series/allSeries/AllSeries";

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
                    <AllClippers
                    />
                } />
                <Route path="/series" element={
                    <AllSeries/>
                }>

                </Route>
                <Route path="/series/:id" element={
                    <SeriesWithId
                    />}
                />
            </Routes>
        </>
    )
}

export default MainContainer;
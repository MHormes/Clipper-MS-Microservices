import React from "react";
import {Route, Routes} from "react-router-dom";
import SeriesWithId from "./pages/series/SeriesWithId";
import LoginScreen from "./components/homePage/LoginScreen";
import Navbar from "./components/siteDefaults/Navbar";
import AllClippers from "./pages/clipper/AllClippers";
import AllSeries from "./pages/series/AllSeries";
import SingleClipper from "./pages/clipper/SingleClipper";

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
                <Route path="/clipper/:id" element={
                    <SingleClipper
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
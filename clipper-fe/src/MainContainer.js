import React from "react";
import {Route, Routes} from "react-router-dom";
import SeriesWithId from "./pages/series/SeriesWithId";
import LoginScreen from "./pages/homePage/LoginScreen";
import Navbar from "./components/siteDefaults/Navbar";
import AllClippers from "./pages/clipper/AllClippers";
import AllSeries from "./pages/series/AllSeries";
import SingleClipper from "./pages/clipper/SingleClipper";
import UpdateClipper from "./pages/clipper/UpdateClipper";
import RouteAuthProvider from "./services/RouteAuthProvider";
import ProtectedRoute from "./services/ProtectedRoute";

const MainContainer = () => {
    return (
        <>
            <RouteAuthProvider>
                <Navbar/>
                <Routes>
                    <Route path="/" element={
                        <LoginScreen
                        />
                    }/>
                    <Route path="/clippers" element={
                        <ProtectedRoute>
                            <AllClippers
                            />
                        </ProtectedRoute>
                    }/>

                    <Route path="/clipper/:id" element={
                        <ProtectedRoute>
                            <SingleClipper
                            />
                        </ProtectedRoute>
                    }/>

                    <Route path="/clipperUpdate/:id" element={
                        <ProtectedRoute>
                            <UpdateClipper
                            />
                        </ProtectedRoute>
                    }/>

                    <Route path="/series" element={
                        <ProtectedRoute>
                            <AllSeries/>
                        </ProtectedRoute>
                    }/>

                    <Route path="/series/:id" element={
                        <ProtectedRoute>
                            <SeriesWithId
                            />
                        </ProtectedRoute>
                    }/>

                </Routes>
            </RouteAuthProvider>
        </>
    )
}

export default MainContainer;
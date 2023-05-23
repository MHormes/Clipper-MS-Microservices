import React from "react";
import {Route, Routes} from "react-router-dom";
import SeriesWithId from "./pages/series/SeriesWithId";
import LoginScreen from "./pages/homePage/LoginScreen";
import Navbar from "./components/siteDefaults/Navbar";
import AllClippers from "./pages/clipper/AllClippers";
import AllSeries from "./pages/series/AllSeries";
import ClipperWithId from "./pages/clipper/ClipperWithId";
import UpdateClipper from "./pages/clipper/UpdateClipper";
import RouteAuthProvider from "./services/RouteAuthProvider";
import ProtectedRoute from "./services/ProtectedRoute";
import CreateSeries from "./pages/series/CreateSeries";
import CreateClipper from "./pages/clipper/CreateClipper";

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
                            <ClipperWithId
                            />
                        </ProtectedRoute>
                    }/>

                    <Route path="/clipper/add" element={
                        <ProtectedRoute>
                            <CreateClipper
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

                    <Route path="/series/add" element={
                        <ProtectedRoute>
                            <CreateSeries
                            />
                        </ProtectedRoute>
                    }/>

                </Routes>
            </RouteAuthProvider>
        </>
    )
}

export default MainContainer;
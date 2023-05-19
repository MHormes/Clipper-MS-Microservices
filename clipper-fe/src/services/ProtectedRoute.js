import {AuthContext} from "./RouteAuthProvider";
import {useContext} from "react";
import {Navigate, useLocation} from "react-router";

const debug = false;
const ProtectedRoute = ({ children }) => {

    const location = useLocation();

    const { tokenValue } = useContext(AuthContext);
    if(debug) console.log(tokenValue);
    if(!tokenValue){
        return <Navigate to={"/"} replace state={{from: location}}/>
    }

    return children;
};

export default ProtectedRoute;
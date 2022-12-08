import {useNavigate} from "react-router";
import {useEffect} from "react";

const LoginScreen = () => {

    const navigate = useNavigate();

    useEffect(() => {
        //todo check token validity
        const token = localStorage.getItem("accessToken");
        if (token) {
            console.log("Token found, redirecting to /clippers");
            navigate("/clippers");
        }
    }, [navigate]);

    return(
        <>

        </>
    )
}

export default LoginScreen;
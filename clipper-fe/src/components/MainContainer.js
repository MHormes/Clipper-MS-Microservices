import React, {useEffect} from "react";

const MainContainer = (props) => {

    useEffect(() => {
        console.log(props)
        localStorage.setItem("accessToken", props)
    })

    return (
        <>
            <h1>Clipper</h1>
        </>
    )
}

export default MainContainer;
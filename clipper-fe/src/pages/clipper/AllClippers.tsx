import React, {useEffect, useState} from "react";
import ClipperList from "../../components/clipper/viewClipper/ClipperList";
import ClipperApi from "../../services/api/ClipperApi";
import type {IClipper,} from "../../services/model/ClipperModel";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";
import {useNavigate} from "react-router";

const clipperApi = new ClipperApi();
const debug = false;
const AllClippers = () => {

    const navigate = useNavigate();
    const [clipperList, setClipperList] = useState([] as IClipper[]);

    useEffect(() => {
        async function getAllClippers() {
            const response = await clipperApi.getAllClippers();
            if(response !== null) {
                setClipperList(response.data);
                if (debug) console.log(response.data);
            }else{
                console.log("Error fetching clippers")
            }
        }

        getAllClippers().then(() => {
            if (debug) console.log("Clipper data fetched!")
        });
    }, [])


    return (
        <div>
            <h1 className="text-4xl text-center m-2">
                All Clippers
            </h1>
            <span className={"btn"} onKeyDown={() => navigate("/clipper/add")} onClick={() => navigate("/clipper/add")}><FontAwesomeIcon icon={faPlus}/></span>
            <ClipperList
                clipperListProp={clipperList}/>

        </div>
    );
}

export default AllClippers;
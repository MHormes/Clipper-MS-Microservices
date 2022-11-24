import React, {useEffect, useState} from "react";
import ClipperList from "../../components/clipper/viewClipper/ClipperList";
import ClipperApi from "../../services/api/ClipperApi";
import type {IClipper} from "../../services/model/ClipperModel";
import {Typography} from "@mui/material";

const clipperApi = new ClipperApi();
const debug = false;
const AllClippers = () => {

    const [clipperList: IClipper[], setClipperList] = useState();

    useEffect(() => {
        async function getAllClippers() {
            const response = await clipperApi.getAllClippers();
            setClipperList(response.data);
            if (debug) console.log(response.data);
        }
        getAllClippers().then(r => {if (debug) console.log("Clipper data fetched!")});
    }, [])

    if(clipperList != null)
    return (
        <>
            <div>
                <Typography variant="h2" align='center' sx={{mt: 2}}>
                    All Clippers
                </Typography>
                <ClipperList
                    clipperListProp={clipperList}/>
            </div>
        </>
    )
}

export default AllClippers;
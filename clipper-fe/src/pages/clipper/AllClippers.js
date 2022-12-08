import React, {useEffect, useState} from "react";
import ClipperList from "../../components/clipper/viewClipper/ClipperList";
import ClipperApi from "../../services/api/ClipperApi";
import type {IClipper,} from "../../services/model/ClipperModel";

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
        getAllClippers().then(() => {if (debug) console.log("Clipper data fetched!")});
    }, [])

    if(clipperList != null)
    return (
        <>
            <div>
                <h1 className="text-4xl text-center m-2">
                    All Clippers
                </h1>
                <ClipperList
                    clipperListProp={clipperList}/>
            </div>

        </>
    )
}

export default AllClippers;
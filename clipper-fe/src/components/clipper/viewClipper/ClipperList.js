import React, { useEffect, useState } from "react";
import ClipperApi from "../../../services/api/ClipperApi";
import ClipperSingle from "./ClipperSingle";

const clipperApi = new ClipperApi();
const debug = false;
const ClipperList = () => {

    const [clipperList, setClipperList] = useState();

    useEffect(() => {
        async function getAllClippers() {
            const response = await clipperApi.getAllClippers();
            setClipperList(response.data);
            if (debug) console.log(response.data);
        }
        getAllClippers().then(r => console.log("Clipper data fetched!"));
    }, [])

    if (clipperList != null)
        return (
            <>
                <div>
                    <h1>Clipper List</h1>
                    <ul>
                        {clipperList.map(
                            (clipper) =>
                                <ClipperSingle
                                    key={clipper.id}
                                    clipperProp={clipper} />
                        )}
                    </ul>
                </div>
            </>
        )
}

export default ClipperList
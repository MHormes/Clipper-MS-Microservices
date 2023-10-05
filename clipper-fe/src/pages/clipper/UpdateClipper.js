import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";
import ClipperForm from "../../components/clipper/changeClipper/ClipperForm";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router";
import ClipperApi from "../../services/api/ClipperApi";
import type {IClipperCreateRequest} from "../../services/model/ClipperModel";

const clipperApi = new ClipperApi();
const debug = false;
const UpdateClipper = () => {

    const navigate = useNavigate();
    const params = useParams();

    const [loading, setLoading] = useState(false);
    const [clipperToUpdate, setClipperToUpdate] = useState();

    useEffect(() => {
        async function getClipperToUpdate() {
            setLoading(true);
            const response = await clipperApi.getClipperWithId(params.id);
            setClipperToUpdate(response.data);
            setLoading(false);
            if (debug) console.log(response.data);
        }

        getClipperToUpdate().then(r => {
            if (debug) console.log("Clipper data fetched!")
        });
    }, [params])

    const updateClipper = async (clipperUpdate: IClipperCreateRequest, image: File) => {
        clipperUpdate.createdBy = "b1e7544d-b5bc-429a-83c8-8b222ae2519f";
        if(clipperUpdate.seriesId === ""){
            clipperUpdate.seriesId = null;
        }
        if (debug) console.log(clipperUpdate);

        setLoading(true);
        let response = await clipperApi.updateClipper(clipperUpdate, image);
        if (debug) console.log(response);

        setLoading(false);
        if (response && response.status === 200) {
            navigate(`/clipper/${response.data.id}`);
        } else {
            alert("Something went wrong");
        }
    }

    return(
        <>
            <h1 className="text-4xl text-center m-2">
                Update Clipper
            </h1>
            {loading ?
                <LoadingSpinner loading={loading} info={"Updating series"}/>
                :
                <ClipperForm
                    formFunction={updateClipper}
                    clipper={clipperToUpdate}
                    mode={"Update"}
                />
            }
        </>
    );

}

UpdateClipper.propTypes = {

}

export default UpdateClipper;
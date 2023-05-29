import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";
import React, {useEffect, useState} from "react";
import ClipperForm from "../../components/clipper/changeClipper/ClipperForm";
import type {IClipperCreateRequest} from "../../services/model/ClipperModel";
import ClipperApi from "../../services/api/ClipperApi";
import {useNavigate} from "react-router";
import type {ISeries} from "../../services/model/SeriesModel";
import SeriesApi from "../../services/api/SeriesApi";

const debug = true;
const clipperApi = new ClipperApi();
const seriesApi = new SeriesApi();
const CreateClipper = () => {

    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const [seriesList: ISeries[], setSeriesList] = useState();

    useEffect(() => {
        async function getAllSeries() {
            const response = await seriesApi.getAllSeries();
            setSeriesList(response.data);
            if (debug) console.log(response.data);
        }

        getAllSeries().then(r => {
            if (debug) console.log("Series data fetched!")
        });
    }, [])

    const createClipper = async (clipperObject: IClipperCreateRequest, image: File) => {
        clipperObject.createdBy = "b1e7544d-b5bc-429a-83c8-8b222ae2519f";
        if(clipperObject.seriesId === ""){
            clipperObject.seriesId = null;
        }
        if (debug) console.log(clipperObject);

        setLoading(true);
        let response = await clipperApi.createClipper(clipperObject, image);
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
                Create Clipper
            </h1>
            {loading ?
                <LoadingSpinner loading={loading} info={"Creating series"}/>
                :
                <ClipperForm
                    formFunction={createClipper}
                    seriesList={seriesList}
                />
            }
        </>
    );
}

export default CreateClipper;
import React, {useState} from "react";
import SeriesForm from "../../components/series/changeSeries/SeriesForm";
import type {ISeriesCreateRequest} from "../../services/model/SeriesModel";
import SeriesApi from "../../services/api/SeriesApi";
import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";
import {useNavigate} from "react-router";

const debug = false;
const seriesApi = new SeriesApi();
const CreateSeries = () => {

    const [loading, setLoading] = useState(false);

    const navigate= useNavigate();

    const createSeries = async (seriesObject: ISeriesCreateRequest, image: File) => {
        //todo refactor to use token value
        seriesObject.createdBy = "b1e7544d-b5bc-429a-83c8-8b222ae2519f";
        if (debug) console.log(seriesObject);

        setLoading(true);
        let response = await seriesApi.createSeries(seriesObject, image);
        if (debug) console.log(response);

        setLoading(false);
        if(response && response.status === 200) {
            navigate(`/series/${response.data.id}`);
        }else {
            alert("Something went wrong");
        }
    }

    return (
        <>
            <h1 className="text-4xl text-center m-2">
                Create series
            </h1>
            {loading ?
                <LoadingSpinner loading={loading} info={"Creating series"}/>
                :
                <SeriesForm
                    formFunction={createSeries}
                />
            }
        </>
    );
}

export default CreateSeries;
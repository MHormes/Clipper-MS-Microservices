import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";
import SeriesForm from "../../components/series/changeSeries/SeriesForm";
import React, {useEffect, useState} from "react";
import SeriesApi from "../../services/api/SeriesApi";
import type {ISeriesCreateRequest} from "../../services/model/SeriesModel";
import {useNavigate, useParams} from "react-router";

const debug = false;
const seriesApi = new SeriesApi();
const UpdateSeries = () => {

    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const params = useParams();

    useEffect(() => {
        async function getSeriesToUpdate() {
            setLoading(true);
            const response = await seriesApi.getSeriesWithId(params.id);
            setSeriesToUpdate(response.data);
            setLoading(false);
            if (debug) console.log(response.data);
        }

        getSeriesToUpdate().then(r => {
            if (debug) console.log("Clipper data fetched!")
        });
    }, [params])

    const [seriesToUpdate, setSeriesToUpdate] = useState();

    const updateSeries = async (seriesObject: ISeriesCreateRequest, image: File) => {
        seriesObject.createdBy = "b1e7544d-b5bc-429a-83c8-8b222ae2519f";

        setLoading(true);
        const response = await seriesApi.updateSeries(seriesObject, image);
        setLoading(false);

        if (response && response.status === 200) {
            navigate(`/series/${response.data.id}`);
        } else {
            alert("Something went wrong");
        }
    }

    return (
        <>
            <h1 className="text-4xl text-center m-2">
                Update series
            </h1>
            {loading ?
                <LoadingSpinner loading={loading} info={"Updating series"}/>
                :
                <SeriesForm
                    formFunction={updateSeries}
                    series={seriesToUpdate}
                    mode={"Update"}
                />
            }
        </>
    )

}

export default UpdateSeries;
import React, {useEffect, useState} from "react";
import SeriesApi from "../../services/api/SeriesApi";
import {useParams} from "react-router";
import ClipperList from "../../components/clipper/viewClipper/ClipperList";
import type {ISeries} from "../../services/model/SeriesModel";
import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";

const seriesApi = new SeriesApi();
const debug = false;
const SeriesWithId = () => {

    const params = useParams();
    const [seriesWithId: ISeries, setSeriesWithId] = useState();

    useEffect(() => {
        async function getSeriesWithId() {
            const response = await seriesApi.getSeriesWithId(params.id);
            setSeriesWithId(response.data);
            if (debug) console.log(response.data);
        }

        getSeriesWithId().then(r => {
            if (debug) console.log("Series data fetched!")
        });
    }, [params])

    return (
        <>
            {seriesWithId != null ?
                <div>
                    <h1 className="text-4xl text-center m-2">
                        Series: {seriesWithId.name}
                    </h1>
                    <ClipperList
                        clipperListProp={seriesWithId.clippers}
                        seriesView={true}
                    />
                </div>
                :
                <LoadingSpinner info={"Loading selected Series"}/>
            }
            </>
        )
}

export default SeriesWithId
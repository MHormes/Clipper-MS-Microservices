import React, {useEffect, useState} from "react";
import SeriesApi from "../../services/api/SeriesApi";
import {useNavigate, useParams} from "react-router";
import ClipperList from "../../components/clipper/viewClipper/ClipperList";
import type {ISeries} from "../../services/model/SeriesModel";
import LoadingSpinner from "../../components/siteDefaults/LoadingSpinner";
import DeleteClipperModal from "../../components/modal/DeleteClipperModal";
import DeleteSeriesModal from "../../components/modal/DeleteSeriesModal";

const seriesApi = new SeriesApi();
const debug = false;
const SeriesWithId = () => {

    const params = useParams();
    const navigate = useNavigate();
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

    async function deleteSeries() {
        console.log("Delete series with id: " + seriesWithId.id);
        const response = await seriesApi.deleteSeries(seriesWithId.id);
        if (debug) console.log(response);
        if (response.status === 200) {
             if (debug) console.log("Series with id: " + seriesWithId.id + " has been deleted!");
            setTimeout(() => {
                navigate("/series");
            }, "1000")
        }
    }

    return (
        <>
            {seriesWithId != null ?
                <div>
                    <h1 className={"text-4xl text-center m-2"}>
                        Series: {seriesWithId.name}
                    </h1>
                    <DeleteSeriesModal deleteSeries={deleteSeries} seriesProp={seriesWithId}/>
                    {seriesWithId.clippers.length > 0 ?
                    <ClipperList
                        clipperListProp={seriesWithId.clippers}
                        seriesView={true}
                    />
                        :
                        <p className={"text-xl text-center m-2 mt-8"}>
                            Series holds no clippers
                        </p>
                    }
                </div>
                :
                <LoadingSpinner info={"Loading selected Series"}/>
            }
            </>
        )
}

export default SeriesWithId
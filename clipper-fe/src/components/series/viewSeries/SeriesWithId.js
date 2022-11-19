import React, { useEffect, useState } from "react";
import SeriesApi from "../../../services/api/SeriesApi";
import ClipperSingle from "../../clipper/viewClipper/ClipperSingle";
import {Grid, Typography} from "@mui/material";
import {useParams} from "react-router";
import ClipperList from "../../clipper/viewClipper/ClipperList";
import type {ISeries} from "../../../services/model/SeriesModel";

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
        getSeriesWithId().then(r => {if (debug) console.log("Series data fetched!")});
    }, [params])

    if (seriesWithId != null)
        return (
            <>
                <div>
                    <Typography variant="h2" align='center' sx={{mt: 2}}>
                        Series: {seriesWithId.name}
                    </Typography>
                    <ClipperList
                        clipperListProp={seriesWithId.clippers}
                        seriesViewProp={true}
                    />
                </div>
            </>
        )
}

export default SeriesWithId
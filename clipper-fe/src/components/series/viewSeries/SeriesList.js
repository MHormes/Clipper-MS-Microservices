import React, {useEffect, useState} from "react";
import {Grid} from "@mui/material";
import type {ISeries} from "../../../services/model/SeriesModel";
import SeriesSingle from "./SeriesSingle";

const debug = false;
const SeriesList = (props) => {

    const [seriesList: ISeries[], setSeriesList] = useState();

    useEffect(() => {
        async function loadSeriesList() {
            setSeriesList(props.seriesListProp)
            if (debug) console.log(props.seriesListProp);
        }
        loadSeriesList().then(r => console.log("Series list assigned!"));
    }, [])

    if (seriesList != null)
        return (
            <>
                <div>
                    <Grid container spacing={12} columns={4} sx={{m: 0}}>
                        {seriesList.map(
                            (series) =>
                                (
                                    <Grid item key={series.id}>
                                        <SeriesSingle
                                            key={series.id}
                                            seriesProp={series}/>
                                    </Grid>
                                ))}
                    </Grid>
                </div>
            </>
        )
}

export default SeriesList
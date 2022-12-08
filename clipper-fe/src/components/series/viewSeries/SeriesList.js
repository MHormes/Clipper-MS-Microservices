import React, {useEffect, useState} from "react";
import type {ISeries} from "../../../services/model/SeriesModel";
import SeriesSingle from "./SeriesSingle";
import PropTypes from "prop-types";

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

    if (seriesList != null) return (<>
        <div className="grid grid-cols-1">
            <ul>
                {seriesList.map((series) => (<li key={series.id}>
                    <SeriesSingle
                        series={series}/>
                </li>))}
            </ul>
        </div>
    </>)
}

SeriesList.propTypes = {
    seriesListProp: PropTypes.array.isRequired
}
export default SeriesList
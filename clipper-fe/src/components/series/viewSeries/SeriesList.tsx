import React, {useEffect, useState} from "react";
import type {ISeries} from "../../../services/model/SeriesModel";
import SeriesSingle from "./SeriesSingle";
import LoadingSpinner from "../../siteDefaults/LoadingSpinner";
import PropTypes from "prop-types";

const debug = false;
const SeriesList = (props) => {

    const [seriesList, setSeriesList] = useState([] as ISeries[]);

    useEffect(() => {
        async function loadSeriesList() {
            setSeriesList(props.seriesListProp)
            if (debug) console.log(props.seriesListProp);
        }

        loadSeriesList().then(r => {
            if (debug) console.log("Series list assigned!")
        });
    }, [props.seriesListProp])

    return (
        <div className="grid grid-cols-1">
            <ul>
                {seriesList != null ?
                    seriesList.map((series) => (<li key={series.id}>
                        <SeriesSingle
                            series={series}/>
                    </li>))
                    :
                    <LoadingSpinner/>
                }
            </ul>
        </div>
    )
}

SeriesList.propTypes = {
    seriesListProp: PropTypes.array
}

export default SeriesList
import React, {useEffect} from "react";
import {useNavigate} from "react-router";
import type {ISeries} from "../../../services/model/SeriesModel";
import PropTypes from "prop-types";
import SeriesCard from "../../card/SeriesCard";

const debug = false;
const SeriesSingle = (props) => {

    //Props gotten from series List component -> holds single series
    const series: ISeries = props.series;

    useEffect(() => {
        if (debug) console.log("SeriesSingle: series: ", series);
    }, [props.series, series]);


    const navigate = useNavigate();

    const viewFullSeries = () => {
        console.log("Redirect user to series list: " + series.id)
        navigate("/series/" + series.id);
    }

    const generateSeriesInfo = () => {
        if (series.custom) {
            return 'This series currently has ' + series.clippers.length + ' clippers'
        }
        return 'The system knows ' + series.clippers.length + ' of the 4 clippers';
    }

    return (<>
            <SeriesCard
                title={series.name ? series.name : "No-Series"}
                series={series}
                imageSource={series.imageData}
                imageAlt="seriesImage"
                seriesInfo={generateSeriesInfo()}
                actionSeries={viewFullSeries}/>
        </>);
}

SeriesSingle.propTypes = {
    series: PropTypes.object.isRequired
}
export default SeriesSingle;


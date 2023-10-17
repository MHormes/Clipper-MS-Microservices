import React, {useEffect, useState} from "react";
import type {ISeries} from "../../../services/model/SeriesModel";
import PropTypes from "prop-types";
import LoadingSpinner from "../../siteDefaults/LoadingSpinner";
import SeriesApi from "../../../services/api/SeriesApi";

const debug = false;
const seriesApi = new SeriesApi();
const SeriesSelect = (props) => {

    const [seriesList, setSeriesList] = useState([] as ISeries[]);

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

    return (
        <div>
            <label className={'label'}>
            <span className={`label-text text-lg`}>
                    Select Series
                </span>
            </label>
            {seriesList != null ?
                <select
                    className={"select w-full max-w-xs"}
                    defaultValue={props.preSelect ? props.preSelect : "Choose series"}
                    onChange={props.onChange}
                >

                    <option disabled>Choose series</option>

                    {seriesList.map((series) => (
                        <option key={series.id} value={series.id}>
                            {series.name}
                        </option>))}
                </select>
                :
                <LoadingSpinner/>
            }
        </div>
    )
}

SeriesSelect.propTypes = {
    onChange: PropTypes.func.isRequired,
    preSelect: PropTypes.string
};

export default SeriesSelect;
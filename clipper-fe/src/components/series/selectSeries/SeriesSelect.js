import React, {useEffect, useState} from "react";
import type {ISeries} from "../../../services/model/SeriesModel";
import PropTypes from "prop-types";
import LoadingSpinner from "../../siteDefaults/LoadingSpinner";

const debug = false;
const SeriesSelect = (props) => {


    const [seriesList: ISeries[], setSeriesList] = useState();

    useEffect(() => {
        async function loadSeriesList() {
            setSeriesList(props.seriesList)
            if (debug) console.log(props.seriesList);
        }

        loadSeriesList().then(r => {
            if (debug) console.log("Series list assigned!")
        });
    }, [props.seriesList])

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
                    defaultValue={"Choose series"}
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
    seriesList: PropTypes.array,
    onChange: PropTypes.func.isRequired
};

export default SeriesSelect;
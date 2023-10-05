import React, {useEffect, useState} from "react";
import type {ISeries} from "../../../services/model/SeriesModel";
import PropTypes, {number} from "prop-types";
import LoadingSpinner from "../../siteDefaults/LoadingSpinner";

const debug = false;
const SeriesNumberSelect = (props) => {


    const [numberList: number[], setNumberList] = useState();

    useEffect(() => {
        async function loadNumberList() {
            setNumberList(props.numberList)
            if (debug) console.log(props.numberList);
        }

        loadNumberList().then(r => {
            if (debug) console.log("Number list assigned!")
        });
    }, [props.numberList])

    return (
        <div>
            <label className={'label'}>
            <span className={`label-text text-lg`}>
                    Select number in series
                </span>
            </label>
            {numberList != null ?
                <select
                    className={"select w-full max-w-xs"}
                    defaultValue={props.preSelect ? props.preSelect :"Choose number in series"}
                    onChange={props.onChange}
                >

                    <option disabled>Choose number in series</option>

                    {numberList.map((number) => (
                        <option key={number} value={number}>
                            {number}
                        </option>))}
                </select>
                :
                <LoadingSpinner/>
            }
        </div>
    )
}

SeriesNumberSelect.propTypes = {
    numberList: PropTypes.array,
    onChange: PropTypes.func.isRequired,
    preSelect: PropTypes.number
};

export default SeriesNumberSelect;
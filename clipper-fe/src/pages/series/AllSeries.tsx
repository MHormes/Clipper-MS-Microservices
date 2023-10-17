import React, {useEffect, useState} from "react";
import type {ISeries} from "../../services/model/SeriesModel";
import SeriesList from "../../components/series/viewSeries/SeriesList";
import SeriesApi from "../../services/api/SeriesApi";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";
import {useNavigate} from "react-router";

const seriesApi = new SeriesApi();
const debug = false;
const AllSeries = () => {
    const navigate = useNavigate();

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
            <h1 className="text-4xl text-center m-2">
                All Series
            </h1>
            <span className={"btn"} onClick={() => navigate("/series/add")}><FontAwesomeIcon icon={faPlus}/></span>
            <SeriesList
                seriesListProp={seriesList}/>
        </div>
    )
}

export default AllSeries;
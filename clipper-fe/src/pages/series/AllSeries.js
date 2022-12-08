import React, {useEffect, useState} from "react";
import type {ISeries} from "../../services/model/SeriesModel";
import SeriesList from "../../components/series/viewSeries/SeriesList";
import SeriesApi from "../../services/api/SeriesApi";

const seriesApi = new SeriesApi();
const debug = false;
const AllSeries = () => {

    const [seriesList: ISeries[], setSeriesList] = useState();

    useEffect(() => {
        async function getAllSeries() {
            const response = await seriesApi.getAllSeries();
            setSeriesList(response.data);
            if (debug) console.log(response.data);
        }

        getAllSeries().then(r => console.log("Series data fetched!"));
    }, [])

    if (seriesList != null)
        return (
            <>
                <div>
                    <h1 className="text-4xl text-center m-2">
                        All Series
                    </h1>
                    <SeriesList
                        seriesListProp={seriesList}/>
                </div>
            </>
        )
}

export default AllSeries;
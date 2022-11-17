import React, { useEffect, useState } from "react";
import SeriesApi from "../../../services/api/SeriesApi";
import ClipperSingle from "../../clipper/viewClipper/ClipperSingle";

const seriesApi = new SeriesApi();
const debug = true;
const SeriesWithId = (props) => {

    const seriesId = props.seriesIdProps;
    const [seriesWithId, setSeriesWithId] = useState();

    useEffect(() => {
        async function getSeriesWithId(seriesId) {
            const response = await seriesApi.getSeriesWithId(seriesId);
            setSeriesWithId(response.data);
            if (debug) console.log(response.data);
        }
        getSeriesWithId(seriesId).then(r => console.log("Series data fetched!"));
    }, [])

    if (seriesWithId != null)
        return (
            <>
                <div>
                    <h1>Series: {seriesWithId.name}</h1>
                    <ul>
                        {seriesWithId.clippers.map(
                            (clipper) =>
                                <ClipperSingle
                                    key={clipper.id}
                                    clipperProp={clipper} />
                        )}
                    </ul>
                </div>
            </>
        )
}

export default SeriesWithId
import { Card, CardMedia, CardContent, Typography, CardActions, Button } from "@mui/material";
import React from "react";
import {useNavigate} from "react-router";
import pic from "../../../assets/clipper_boarding.jpg";
import type {ISeries} from "../../../services/model/SeriesModel";

const debug = false;
const SeriesSingle = (props) => {

    //Props gotten from series List component -> holds single series
    const series: ISeries = props.seriesProp;
    if (debug) console.log("SeriesSingle: series: ", series);

    const navigate = useNavigate();

    const viewFullSeries = (series) => {
        navigate("/series/" + series.id);
        console.log("Redirect user to series list: " + series.id)
    }

    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardContent>
            <CardMedia
                component="img"
                height="500"
                image={pic}
                alt={series.name}
            />
                <Typography gutterBottom variant="h5" component="div">
                    {series.name}
                </Typography>
                <Typography variant="body1">
                    {series.custom ? (
                        <span>This series currently has {series.clippers.length} clippers</span>
                    ) : (
                        <span>The system knows {series.clippers.length} of the 4 clippers</span>
                    )}
                </Typography>
            </CardContent>
            <CardActions>
                    <Button size="small" onClick={() => viewFullSeries(series)}>View full series</Button>
            </CardActions>
        </Card>
    );
}

export default SeriesSingle;


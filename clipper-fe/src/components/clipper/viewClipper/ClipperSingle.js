import {Card, CardMedia, CardContent, Typography, CardActions, Button} from "@mui/material";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import pic from "../../../assets/clipper-aansteker.jpg";
import type {IClipper} from "../../../services/model/ClipperModel";


const ClipperSingle = (props) => {

    const navigate = useNavigate();

    const [clipper: IClipper, setClipper] = useState();

    useEffect(() => {
        //Props gotten from clipper List component -> holds single clipper
        setClipper(props.clipperProp);
    }, []);

    const goToClipper = (clipper) => {
        navigate(`/clipper/${clipper}`);
        console.log("Redirect user to page of clipper: " + clipper)
    }

    const viewFullSeries = (series) => {
        navigate(`/series/${series}`);
        console.log("Redirect user to series list: " + series)
    }

    if (clipper != null)
        return (
            <Card sx={{maxWidth: 345}}>
                <CardMedia
                    component="img"
                    height="500"
                    image={pic}
                    alt={clipper.name}
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {clipper.name}
                    </Typography>
                    <Typography variant="body1">
                        {clipper.series && !clipper.series.custom ? (
                            <span>Number {clipper.seriesNumber} of 4</span>
                        ) : (
                            <span>Number {clipper.seriesNumber} in a custom series</span>
                        )}
                    </Typography>
                    <Typography variant="body2">
                        {clipper.series &&
                            <span>From series: {clipper.series.name}</span>
                        }
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small" onClick={() => goToClipper(clipper.id)}>View clipper</Button>

                    { (clipper.series != null && !props.seriesViewProp) &&
                        <Button size="small" onClick={() => viewFullSeries(clipper.series.id)}>
                            View full series
                        </Button>
                    }
                </CardActions>
            </Card>
        );
}

export default ClipperSingle


import React, {useEffect, useState} from "react";
import {
    Typography,
    ButtonGroup,
    Button,
    CardActionArea,
    Card,
    CardMedia,
    CardContent,
    CardActions,
    Grid,
    IconButton
} from "@mui/material";

import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ClipperApi from "../../services/api/ClipperApi";
import {useNavigate, useParams} from "react-router";
import pic from "../../assets/clipper-aansteker-cut.jpg";
import type {IClipper} from "../../services/model/ClipperModel";

const clipperApi = new ClipperApi();

const debug = true;
const SingleClipper = () => {

    //STATE
    const [clipperWithId: IClipper, setClipperWithId] = useState();

    //REACT USAGES
    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function getClipperWithId() {
            const response = await clipperApi.getClipperWithId(params.id);
            setClipperWithId(response.data);
            if (debug) console.log(response.data);
        }

        getClipperWithId().then(r => {
            if (debug) console.log("Series data fetched!")
        });
    }, [params])

    const viewFullSeries = (series) => {
        navigate(`/series/${series}`);
        console.log("Redirect user to series list: " + series)
    }

    const updateClipper = () => {

    }

    const deleteClipper = async () => {
        console.log("Delete clipper with id: " + clipperWithId.id);
        //todo create model to remove clipper
        const response = await clipperApi.deleteClipper(clipperWithId.id);
        if (debug) console.log(response);
        if(response.status === 200){
            console.log("Clipper with id: " + clipperWithId.id + " has been deleted!");
            //todo add timed redirect to .... (clipper list?)
        }
    }

    if (clipperWithId != null)
        return (
            <>
                <Card sx={{m: 4}}>
                    <CardContent>
                        <Grid container spacing={2}>
                            <Grid item xs={3} textalign="center">
                                <Typography variant="h4" align='center' sx={{mt: 2}}>
                                    Clipper: {clipperWithId.name}
                                    <br/>
                                </Typography>
                                <Typography variant="h6" align='center' sx={{mt: 2}}>
                                    Clipper is # {clipperWithId.seriesNumber} in series:
                                </Typography>
                                <Button size="large" variant="contained" textalign='center' sx={{mt: 2}} onClick={() => viewFullSeries(clipperWithId.series.id)}>
                                    {clipperWithId.series.name}
                                </Button>
                                <Typography variant="subtitle2" align='center' sx={{mt: 2}}>
                                    Clipper created by: {clipperWithId.createdById}
                                </Typography>
                            </Grid>
                            <Grid item xs={6} >
                                <CardMedia
                                    component="img"
                                    image={pic}
                                    alt={clipperWithId.name}
                                    sx={{width: 100, height: 500}}
                                />
                            </Grid>
                            <Grid item xs={3} justifyContent="right">
                                <IconButton aria-label="add-collection" size="large">
                                    <FavoriteIcon/>
                                </IconButton>
                            </Grid>
                        </Grid>
                    </CardContent>
                    <CardActions>
                        <ButtonGroup variant="contained" sx={{alignContent: 'center'}}>
                            <IconButton aria-label="edit" size="large" onClick={() => updateClipper()}>
                                <EditIcon/>
                            </IconButton>
                            <IconButton aria-label="delete" size="large" onClick={() => deleteClipper()}>
                                <DeleteIcon/>
                            </IconButton>
                        </ButtonGroup>
                    </CardActions>
                </Card>
            </>
        )
}

export default SingleClipper;
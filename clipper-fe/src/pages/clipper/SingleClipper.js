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
import {useParams} from "react-router";
import pic from "../../assets/clipper-aansteker-cut.jpg";
import type {IClipper} from "../../services/model/ClipperModel";

const clipperApi = new ClipperApi();

const debug = true
const SingleClipper = () => {

    const [clipperWithId: IClipper, setClipperWithId] = useState();

    const params = useParams();

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

    if (clipperWithId != null)
        return (
            <>
                <Card sx={{m: 4}}>
                    <CardContent>
                        <Grid container spacing={2}>
                            <Grid item xs={3} textAlign="center">
                                <Typography variant="h4" align='center' sx={{mt: 2}}>
                                    Clipper: {clipperWithId.name}
                                    <br/>
                                </Typography>
                                <Typography variant="h6" align='center' sx={{mt: 2}}>
                                    Clipper is # {clipperWithId.seriesNumber} in series:
                                </Typography>
                                <Button size="large" variant="contained" textAlign='center' sx={{mt: 2}}>
                                    {clipperWithId.series.name}
                                </Button>
                                <Typography variant="subtitle2" align='bottom' sx={{mt: 2}}>
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
                            <IconButton aria-label="edit" size="large">
                                <EditIcon/>
                            </IconButton>
                            <IconButton aria-label="delete" size="large">
                                <DeleteIcon/>
                            </IconButton>
                        </ButtonGroup>
                    </CardActions>
                </Card>
            </>
        )
}

export default SingleClipper;
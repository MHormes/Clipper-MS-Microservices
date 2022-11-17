import { Card, CardMedia, CardContent, Typography, CardActions, Button } from "@mui/material";
import React from "react";

const ClipperSingle = (props) => {

  //Props gotten from clipper List component -> holds single clipper
  const clipper = props.clipperProp;

  const goToClipper = (clipper) => {
    console.log("Redirect user to page of clipper: " + props.clipperProp)
  }

  const viewFullSeries = (seriesId) => {
    console.log("Redirect user to series list: " + JSON.parse(JSON.stringify(seriesId)))
  }




  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        component="img"
        height="140"
        image="/static/images/cards/contemplative-reptile.jpg"
        alt={clipper.name}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {clipper.name}
        </Typography>
        <Typography variant="body1">
          {clipper.seriesId && !clipper.seriesId.custom ? (
            <span>Number {clipper.seriesNumber} of 4</span>
          ) : (
            <span>Number {clipper.seriesNumber} in a custom series</span>
          )}
        </Typography>
        <Typography variant="body2">
          {clipper.seriesId &&
            <span>From series: {clipper.seriesId.name}</span>
          }
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" onClick={() => goToClipper(clipper)}>Go to clipper page</Button>

        {clipper.seriesId &&
          <Button size="small" onClick={() => viewFullSeries(clipper.seriesId)}>View full series</Button>
        }
      </CardActions>
    </Card>
  );
}

export default ClipperSingle


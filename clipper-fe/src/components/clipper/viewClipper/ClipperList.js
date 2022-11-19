import React, {useEffect, useState} from "react";
import ClipperSingle from "./ClipperSingle";
import {Grid} from "@mui/material";
import type {IClipper} from "../../../services/model/ClipperModel";

const debug = false;
const ClipperList = (props) => {

    const [clipperList: IClipper[], setClipperList] = useState();

    useEffect(() => {
        async function loadClipperList() {
            setClipperList(props.clipperListProp)
            console.log(props.seriesViewProp)
            if (debug) console.log(props.clipperListProp);
        }

        loadClipperList().then(r => {
            if (debug) console.log("Clipper list assigned!")
        });
    }, [])

    if (clipperList != null)
        return (
            <>
                <div>
                    <Grid container spacing={12} columns={4} sx={{m: 0}}>
                        {clipperList.map(
                            (clipper) =>
                                (
                                    <Grid item key={clipper.id}>
                                        <ClipperSingle
                                            key={clipper.id}
                                            clipperProp={clipper}
                                            seriesViewProp={props.seriesViewProp}
                                        />
                                    </Grid>
                                ))}
                    </Grid>
                </div>
            </>
        )
}

export default ClipperList
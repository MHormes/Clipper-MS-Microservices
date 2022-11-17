import { Card, CardContent, Typography } from "@mui/material";
import React from "react";

const MainContainer = () => {
    return (
        <>
            <h1>Clipper</h1>
            <Card  >
                <CardContent>
                    <Typography variant="h5">
                        h1. heading
                    </Typography>
                    <Typography variant="h1" component="h2">
                        h1. Heading
                    </Typography>

                </CardContent>
            </Card>
        </>
    )
}

export default MainContainer;
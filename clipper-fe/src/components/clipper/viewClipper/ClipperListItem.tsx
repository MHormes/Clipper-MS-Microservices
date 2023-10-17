import React, {useEffect,} from "react";
import ClipperCard from "../../card/ClipperCard";
import {useNavigate} from "react-router";
import PropTypes, {bool} from "prop-types";

const debug = false;
const ClipperListItem = (props) => {
    const navigate = useNavigate();
    const clipper = props.clipper;

    useEffect(() => {
        if (debug) console.log(props.clipper);
    }, [props.clipper]);

    const goToClipper = () => {
        console.log("Redirect user to page of clipper: " + clipper.name);
        navigate(`/clipper/${clipper.id}`);
    }

    const viewFullSeries = () => {
        console.log("Redirect user to series list: " + clipper.series.name);
        navigate(`/series/${clipper.series.id}`);
    }

    const generateSeriesNumber = () =>{
        if (clipper.series && !clipper.series.custom) {
            return '# ' + clipper.seriesNumber + ' of 4 in series:';
        } else if(clipper.series) {
            return '# ' + clipper.seriesNumber + ' in custom series:';
        }else{
            return 'Clipper is not part of a series';
        }
    }

    const generateSeriesName = () =>{
        if (clipper.series && clipper.series.name) {
            return clipper.series.name;
        } else {
            return 'No-Series';
        }
    }

    if (clipper !== null) {
        return (

        <>
            <ClipperCard
                title={props.clipper.name}
                imageSource={props.clipper.imageData}
                imageAlt="clipperImage"
                seriesNumber={generateSeriesNumber()}
                seriesName={generateSeriesName()}
                actionClipper={goToClipper}
                actionSeries={viewFullSeries}
                seriesView={props.seriesView}
                />
        </>
        );
    }
}

ClipperListItem.propTypes = {
    clipper: PropTypes.object.isRequired,
    seriesView: bool
}
export default ClipperListItem;


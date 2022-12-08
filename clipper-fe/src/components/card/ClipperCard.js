import React from "react";
import PropTypes from "prop-types";
import CardPicture from "./CardPicture";
import CardButton from "./CardButton";
import CardText from "./CardText";
import CardTitle from "./CardTitle";

const ClipperCard = (props) => {
    return (
        <div className="card card-side bg-white h-28 m-3 md:m-8">
            <CardPicture
                alt={props.imageAlt}
                imageSource={props.imageSource}
            />
            <div className="card-body p-4">
                <CardTitle
                    cardTitle={props.title}
                />
                <CardText
                    cardText={props.seriesNumber}
                />
                <CardText cardText={props.seriesName}/>
            </div>
            <div>
                {!props.seriesView &&
                    <CardButton
                        buttonText="View Series"
                        buttonAction={props.actionSeries}/>
                }
                <CardButton
                    buttonText="View clipper"
                    buttonAction={props.actionClipper}/>
            </div>
        </div>
    );
}

ClipperCard.propTypes = {
    title: PropTypes.string.isRequired,
    imageSource: PropTypes.string.isRequired,
    imageAlt: PropTypes.string.isRequired,
    seriesNumber: PropTypes.string.isRequired,
    seriesName: PropTypes.string.isRequired,
    actionSeries: PropTypes.func.isRequired,
    actionClipper: PropTypes.func.isRequired,
}

ClipperCard.defaultProps = {
    seriesView: false
}

export default ClipperCard
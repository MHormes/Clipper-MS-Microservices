import PropTypes from "prop-types";
import CardPicture from "./CardPicture";
import CardTitle from "./CardTitle";
import CardText from "./CardText";
import CardButton from "./CardButton";
import React from "react";

const SeriesCard = (props) => {

    return (
        <div className="card bg-white m-3  md:m-8">
            <div className="card-body p-4">
                <CardTitle
                    cardTitle={props.title}
                />
                <CardText cardText={props.seriesInfo}
                />
            </div>
            <CardPicture
                alt={props.imageAlt}
                imageSource={props.imageSource}
                centralPic={true}
            />
            <CardButton
                buttonText="View full series"
                buttonAction={props.actionSeries}/>
        </div>)

}
SeriesCard.propTypes = {
    series: PropTypes.object.isRequired,
    imageSource: PropTypes.string.isRequired,
    imageAlt: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    seriesInfo: PropTypes.string.isRequired,
    actionSeries: PropTypes.func.isRequired
}

export default SeriesCard;
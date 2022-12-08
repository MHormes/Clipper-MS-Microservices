import React from "react";
import PropTypes from "prop-types";

const CardPicture = (props) => {
    return (
        <img
            className={"object-scale-down m-2  " + (props.centralPic ? 'px-10 content-center' : 'ml-4' )}
            src={props.imageSource}
            alt={props.alt}/>
    );
}

CardPicture.propTypes = {
    imageSource: PropTypes.string.isRequired,
    alt: PropTypes.string.isRequired,
}

CardPicture.defaultProps = {
    centralPic: false
}

export default CardPicture;
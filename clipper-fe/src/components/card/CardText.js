import React from "react";
import PropTypes from "prop-types";

const CardText = (props) => {
    return (
        <>
            <span className={`${props.textSmall? 'text-xs' : 'text-md'}`}>
                {props.cardText}
            </span>
        </>
    );
};

CardText.propTypes = {
    cardText: PropTypes.string.isRequired,
};

CardText.defaultProps = {
    textSmall: false,
}
export default CardText
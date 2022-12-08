import React from "react";
import PropTypes from "prop-types";

const CardText = (props) => {
    return (
        <>
            <p className={" " + (props.textSmall? 'text-xs' : 'text-md')}>
                {props.cardText}
            </p>
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
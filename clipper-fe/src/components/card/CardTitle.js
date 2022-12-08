import React from "react";
import PropTypes from "prop-types";

const CardTitle = (props) => {
    return (
        <>
            <h1 className="card-title text-md">
                {props.cardTitle}
            </h1>
        </>
    );
};

CardTitle.propTypes = {
    cardTitle: PropTypes.string.isRequired,
};

export default CardTitle;
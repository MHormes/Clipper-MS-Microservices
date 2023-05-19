import React from "react";
import PropTypes from "prop-types";

const CardTextBox = (props) => {
    return (
        <>
            <label className={`label`}>
                <span className={`label-text text-lg`}>
                    {props.boxLabel}
                </span>
            </label>
            <input type="text" placeholder={props.boxHint} className="input input-bordered" />
        </>
    );
};

CardTextBox.propTypes = {
    boxHint: PropTypes.string.isRequired,
    boxLabel: PropTypes.string.isRequired,
};

export default CardTextBox
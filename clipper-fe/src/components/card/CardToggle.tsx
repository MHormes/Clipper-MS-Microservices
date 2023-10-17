import React from "react";
import PropTypes from "prop-types";

const CardToggle = (props) => {

    return (
        <div className={"grid grid-cols-3 pt-2"}>
            <span className={`label-text`}>
                {props.firstOption}
            </span>
            <div className={"w-full text-center"}>
            <input type="checkbox"
                   name={props.fieldName}
                   checked={props.value}
                   onChange={props.onChange}
                   className="toggle"
            />
            </div>
            <span className={`label-text text-right`}>
                {props.secondOption}
            </span>
        </div>
    );
}

CardToggle.prototype = {
    firstOption: PropTypes.string.isRequired,
    secondOption: PropTypes.string.isRequired,
    fieldName: PropTypes.string.isRequired,
    value: PropTypes.bool.isRequired,
    onChange: PropTypes.func.isRequired
}

export default CardToggle;
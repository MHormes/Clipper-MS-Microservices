import PropTypes from "prop-types";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React from "react";


const CardIconButton = (props) => {

    const handleClick = () => {
        console.log('Button clicked')
        props.buttonAction();
    }

    return (
            <button className="btn text-white m-1 text-xs"
                    onClick={() => handleClick()}>
                <FontAwesomeIcon icon={props.buttonIcon}/>
            </button>
    );
}

CardIconButton.propTypes = {
    buttonAction: PropTypes.func.isRequired,
    buttonIcon: PropTypes.object.isRequired
}

export default CardIconButton;
import React from "react";
import PropTypes from "prop-types";

const CardImageUpload = (props) => {

    return (
        <>
            <label className={`label`}>
                <span className={`label-text text-lg`}>
                    {props.fileLabel}
                </span>
            </label>
            <input type="file"
                   className="file-input"
                   onChange={props.onChange}
            />
        </>
    );
 }

 CardImageUpload.propTypes = {
     fileLabel: PropTypes.string.isRequired,
     onChange: PropTypes.func.isRequired
 }
 export default CardImageUpload;
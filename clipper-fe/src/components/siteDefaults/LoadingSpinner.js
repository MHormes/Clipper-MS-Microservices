import PropTypes from "prop-types";
import React from "react";

const LoadingSpinner = (props) => {


    return(
        <div>
            <div className="f-full relative items-center block p-6 rounded-lg animate-pulse mt-3">
                <div className="flex items-center justify-center">
                    <div
                        className="inline-block h-8 w-8 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
                        role="status">
                    <span
                        className="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]"
                    >
                        Loading...
                    </span>
                    </div>
                </div>
                <div className="flex items-center justify-center mt-3">
                    <p className="text-sm">{props.info}</p>
                </div>
            </div>
        </div>
    );
}

LoadingSpinner.propTypes = {
    loading: PropTypes.bool.isRequired,
    info: PropTypes.string.isRequired,
}

LoadingSpinner.defaultProps = {
    loading: false,
    info: "Loading...",
}

export default LoadingSpinner;


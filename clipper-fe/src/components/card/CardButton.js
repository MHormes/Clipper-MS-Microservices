import PropTypes from "prop-types";

const CardButton = (props) => {

    const handleClick = () => {
        console.log('Button clicked')
        props.buttonAction();
    }

    return (
        <div className="card-actions justify-end">
            <button className="btn text-white m-1 text-xs"
                onClick={() => handleClick()}>
                {props.buttonText}
            </button>
        </div>
    );
}

CardButton.propTypes = {
    buttonText: PropTypes.string.isRequired,
    buttonAction: PropTypes.func.isRequired
}

export default CardButton;
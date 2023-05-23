import CardToggle from "../../card/CardToggle";
import CardTextBox from "../../card/CardTextBox";
import CardImageUpload from "../../card/CardImageUpload";
import CardButton from "../../card/CardButton";
import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import SeriesForm from "../../series/changeSeries/SeriesForm";

const ClipperForm = (props) => {

    const [clipperObject, setClipperObject] = useState({
        id: "",
        name: "",
        seriesNumber: 0,
        seriesId: "",
        createdBy: "",
    });

    const [selectedImage, setSelectedImage] = useState(null);

    useEffect(() => {
        if (props.clipper) {
            setClipperObject(props.clipper);
        }
    }, [props.clipper]);

    const onChange = e => {
        setClipperObject({
            ...clipperObject,
            [e.target.name]: e.target.value
        });
    }

    const onImageChange = e => {
        setSelectedImage(e.target.files[0]);
    }

    return (
        <div className={"card bg-base-300 m-3"}>
            <div className={"card-body"}>
                <CardTextBox
                    boxHint={"Name"}
                    boxLabel={"Clipper name"}
                    fieldName={"name"}
                    onChange={onChange}
                    value={clipperObject.name}
                />
                <CardImageUpload
                    fileLabel={"Clipper image"}
                    onChange={onImageChange}
                />
                {selectedImage &&
                    <img src={URL.createObjectURL(selectedImage)} alt={"Clipper image preview"}/>
                }
                <CardButton
                    buttonText={props.mode}
                    buttonAction={() => props.formFunction(clipperObject, selectedImage)}
                />
            </div>
        </div>
    );
}

ClipperForm.propTypes = {
    mode: PropTypes.string,
    clipper: PropTypes.object,
    formFunction: PropTypes.func.isRequired
};

ClipperForm.defaultProps = {
    mode: "Create",
};

export default ClipperForm;
import CardTextBox from "../../card/CardTextBox";
import CardButton from "../../card/CardButton";
import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import CardToggle from "../../card/CardToggle";
import CardImageUpload from "../../card/CardImageUpload";

const SeriesForm = (props) => {
    const [seriesObject, setSeriesObject] = useState({
        id: "",
        name: "",
        custom: false,
        createdBy: "",
    });

    const [selectedImage, setSelectedImage] = useState(null);

    useEffect(() => {
        if (props.series) {
            setSeriesObject(props.series);
        }
    }, [props.series]);

    const onChange = e => {
        setSeriesObject({
            ...seriesObject,
            [e.target.name]: e.target.value
        });
    }

    const defineImageUrl = () => {
        if (selectedImage) {
            console.log("selected")
            return URL.createObjectURL(selectedImage);
        } else if (props.series) {
            return `data:image/png;base64, ${props.series.imageData}`;
        } else {
            return "";
        }
    }

    const onToggle = e => {
        setSeriesObject({
            ...seriesObject,
            [e.target.name]: !seriesObject[e.target.name]
        });
    }

    const onImageChange = e => {
        setSelectedImage(e.target.files[0]);
    }

    return (
        <div className={"card bg-base-300 m-3"}>
            <div className={"card-body"}>
                <CardToggle
                    firstOption={"Default series"}
                    secondOption={"Custom series"}
                    fieldName={"custom"}
                    onChange={onToggle}
                    value={seriesObject.custom}
                />
                <CardTextBox
                    boxHint={"Name"}
                    boxLabel={"Series name"}
                    fieldName={"name"}
                    onChange={onChange}
                    value={seriesObject.name}
                />
                <CardImageUpload
                    fileLabel={"Series image"}
                    onChange={onImageChange}
                />
                {defineImageUrl() !== "" &&
                    <img src={defineImageUrl()} alt={"Series image preview"} className={"object-scale-down h-fit p-2"}/>
                }
                <CardButton
                    buttonText={props.mode}
                    buttonAction={() => props.formFunction(seriesObject, selectedImage)}
                />
            </div>
        </div>);
}


SeriesForm.propTypes = {
    mode: PropTypes.string,
    series: PropTypes.object,
    formFunction: PropTypes.func.isRequired
};

SeriesForm.defaultProps = {
    mode: "Create",
};
export default SeriesForm;
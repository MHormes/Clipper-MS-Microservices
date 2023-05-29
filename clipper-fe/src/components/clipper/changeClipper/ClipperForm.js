import CardTextBox from "../../card/CardTextBox";
import CardImageUpload from "../../card/CardImageUpload";
import CardButton from "../../card/CardButton";
import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import SeriesSelect from "../../series/selectSeries/SeriesSelect";
import SeriesApi from "../../../services/api/SeriesApi";
import SeriesNumberSelect from "../../series/selectSeries/SeriesNumberSelect";

const seriesApi = new SeriesApi();
const ClipperForm = (props) => {

    const [clipperObject, setClipperObject] = useState({
        id: "",
        name: "",
        seriesNumber: 0,
        seriesId: "",
        createdBy: "",
    });

    const [selectedImage, setSelectedImage] = useState(null);

    const [availableSeriesNumbers: [], setAvailableSeriesNumbers] = useState([]);

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

    const onSeriesChange = async e => {
        setClipperObject({
            ...clipperObject,
            seriesId: e.target.value
        });
        await assignAvailableSeriesNumbers(e.target.value);
    }

    const onNumberChange = e => {
        setClipperObject({
            ...clipperObject,
            seriesNumber: e.target.value
        });
    }

    const assignAvailableSeriesNumbers = async (seriesId) => {
        let availableNumbers: number[] = await seriesApi.getAvailableSeriesNumbers(seriesId);
        setAvailableSeriesNumbers(availableNumbers);
        console.log(availableSeriesNumbers)
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
                <SeriesSelect
                    seriesList={props.seriesList}
                    onChange={onSeriesChange}
                />
                {clipperObject.seriesId != null && clipperObject.seriesId !== "" &&
                <SeriesNumberSelect
                    numberList={availableSeriesNumbers}
                    onChange={onNumberChange}
                />
                }
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
    formFunction: PropTypes.func.isRequired,
    seriesList: PropTypes.array,
};

ClipperForm.defaultProps = {
    mode: "Create",
};

export default ClipperForm;
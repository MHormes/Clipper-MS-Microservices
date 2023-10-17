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
    const [availableSeriesNumbers, setAvailableSeriesNumbers] = useState([] as number[]);

    useEffect(() => {
        async function assignUpdateValues() {
            setClipperObject({
                id: props.clipper.id,
                name: props.clipper.name,
                seriesNumber: props.clipper.seriesNumber,
                seriesId: props.clipper.series.id,
                createdBy: props.clipper.createdBy,
            });
            await assignAvailableSeriesNumbers(props.clipper.series.id);
        }

        if (props.clipper) {
            assignUpdateValues().then(r => {
                console.log("Update values assigned")
            });
        }
    }, [props.clipper]);

    const defineImageUrl = () => {
        if (selectedImage) {
            console.log("selected")
            return URL.createObjectURL(selectedImage);
        } else if (props.clipper) {
            return `data:image/png;base64, ${props.clipper.imageData}`;
        } else {
            return "";
        }
    }

    const onChange = e => {
        setClipperObject({
            ...clipperObject,
            [e.target.name]: e.target.value
        });
    }

    const onImageChange = e => {
        setSelectedImage(e.target.files[0]);
        defineImageUrl();
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
        //if we edit -> add the numbers instead of replace
        if(props.mode === "Update"){
            setAvailableSeriesNumbers([props.clipper.seriesNumber, ...availableNumbers]);
        }else{
            setAvailableSeriesNumbers(availableNumbers);
        }
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
                    onChange={onSeriesChange}
                    preSelect={props.mode === "Update" && props.clipper ? props.clipper.series.id : null}
                />
                {clipperObject.seriesId != null && clipperObject.seriesId !== "" &&
                    <SeriesNumberSelect
                        numberList={availableSeriesNumbers}
                        onChange={onNumberChange}
                        preSelect={props.mode === "Update" && props.clipper ? props.clipper.seriesNumber : null}
                    />
                }
                <CardImageUpload
                    fileLabel={"Clipper image"}
                    onChange={onImageChange}
                />
                {defineImageUrl() !== "" &&
                    <img src={defineImageUrl()} alt={"Clipper image preview"} className={"object-scale-down h-fit p-2"}/>
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
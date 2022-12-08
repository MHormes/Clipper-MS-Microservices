import React, {useEffect, useState} from "react";
import ClipperApi from "../../services/api/ClipperApi";
import {useNavigate, useParams} from "react-router";
import pic from "../../assets/clipper-aansteker.jpg";
import type {IClipper} from "../../services/model/ClipperModel";
import CardPicture from "../../components/card/CardPicture";
import CardTitle from "../../components/card/CardTitle";
import CardText from "../../components/card/CardText";
import CardButton from "../../components/card/CardButton";
import {faHeart, faPen, faTrashCan} from "@fortawesome/free-solid-svg-icons";
import CardIconButton from "../../components/card/CardIconButton";
import DeleteModal from "../../components/modal/DeleteModal";

const clipperApi = new ClipperApi();

const debug = true;
const SingleClipper = () => {

    //STATE
    const [clipperWithId: IClipper, setClipperWithId] = useState();

    //REACT USAGES
    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function getClipperWithId() {
            const response = await clipperApi.getClipperWithId(params.id);
            setClipperWithId(response.data);
            if (debug) console.log(response.data);
        }

        getClipperWithId().then(r => {
            if (debug) console.log("Series data fetched!")
        });
    }, [params])

    const viewFullSeries = () => {
        navigate(`/series/${clipperWithId.series?.id}`);
        console.log("Redirect user to series list: " + clipperWithId.series?.id)
    }

    const updateClipper = () => {
        console.log("Update clipper: " + clipperWithId.id)
    }

    const addToFavorite = () => {
        console.log("Add clipper to favorite: " + clipperWithId.id)
    }

    const deleteClipper = async () => {
        console.log("Delete clipper with id: " + clipperWithId.id);
        //todo create model to remove clipper
        const response = await clipperApi.deleteClipper(clipperWithId.id);
        if (debug) console.log(response);
        if (response.status === 200) {
            console.log("Clipper with id: " + clipperWithId.id + " has been deleted!");
            //todo add timed redirect to .... (clipper list?)
            setTimeout(() => {
                navigate(-1);
            }, "1000")
        }
    }

    if (clipperWithId != null)
        return (
            <div className="card bg-white m-3">
                <div className="flex items-center justify-center w-full">
                    <CardPicture imageSource={pic} alt="singleClipperImg" centralPic={true}/>
                </div>
                <div className="card-body p-4">
                    <CardTitle cardTitle={'Clipper: ' + clipperWithId.name}/>
                    <CardText cardText={'Clipper is #' + clipperWithId.seriesNumber + ' in series:'}/>
                    <CardText cardText={clipperWithId.series?.name}/>
                </div>
                <div className="card-actions flex justify-between">
                    <CardIconButton buttonIcon={faHeart} buttonAction={addToFavorite}/>
                    <CardIconButton buttonIcon={faPen} buttonAction={updateClipper}/>
                    <DeleteModal deleteClipper={deleteClipper} clipperProp={clipperWithId}/>
                    <CardButton buttonText="View full series" buttonAction={viewFullSeries}/>
                </div>
                <div className="p-2 text-center">
                <CardText cardText={'Clipper created by: ' + clipperWithId.createdById} textSmall={true}/>
                </div>
            </div>
        );
}

export default SingleClipper;
import * as React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
// @ts-ignore
import PropTypes from "prop-types";
import type {ISeries} from "../../services/model/SeriesModel";

const DeleteSeriesModal = (props) => {

    const series: ISeries = props.seriesProp;
    return (
        <div className={"text-right"}>
            {/* The button to open modal */}
            <label htmlFor="deleteModal" className="btn text-white m-3">
                <FontAwesomeIcon icon={faTrashCan}/>
            </label>
            <input type="checkbox" id="deleteModal" className="modal-toggle"/>
            <label htmlFor="deleteModal" className="modal cursor-pointer">
                <label className="modal-box relative" htmlFor="">
                    <h3 className="font-bold text-lg">Are you sure you want to delete {series.name}?</h3>
                    <p className="py-4">This action cannot be undone! All clippers belonging to this series (and all collected clippers) will be deleted
                        subsequently.</p>
                    <div className="modal-action">
                        <label htmlFor="deleteModal" className="btn" onClick={() => props.deleteSeries()}>Yes</label>
                        <label htmlFor="deleteModal" className="btn">No!</label>
                    </div>
                </label>
            </label>
        </div>
    );
}

DeleteSeriesModal.propTypes = {
    seriesProp: PropTypes.object.isRequired,
    deleteSeries: PropTypes.func.isRequired
}

export default DeleteSeriesModal;
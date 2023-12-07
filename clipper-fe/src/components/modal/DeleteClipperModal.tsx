import * as React from 'react';
import type {IClipper} from "../../services/model/ClipperModel";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import PropTypes from "prop-types";

const DeleteClipperModal = (props) => {

    const clipper: IClipper = props.clipperProp;
    return (
        <div>
            {/* The button to open modal */}
            <label htmlFor="deleteModal" className="btn text-white m-1">
                <FontAwesomeIcon icon={faTrashCan}/>
            </label>
            <input type="checkbox" id="deleteModal" className="modal-toggle"/>
            <label htmlFor="deleteModal" className="modal cursor-pointer">
                <label className="modal-box relative" htmlFor="">
                    <h3 className="font-bold text-lg">Are you sure you want to delete {clipper.name}?</h3>
                    <p className="py-4">This action cannot be undone! All collected clippers using this will be deleted
                        subsequently.</p>
                    <div className="modal-action">
                        <label htmlFor="deleteModal" className="btn" onKeyDown={() => props.deleteClipper()} onClick={() => props.deleteClipper()}>Yes</label>
                        <label htmlFor="deleteModal" className="btn">No!</label>
                    </div>
                </label>
            </label>
        </div>
    );
}

DeleteClipperModal.propTypes = {
    clipperProp: PropTypes.object.isRequired,
    deleteClipper: PropTypes.func.isRequired
}

export default DeleteClipperModal;
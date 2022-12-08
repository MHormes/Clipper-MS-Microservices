import * as React from 'react';
import type {IClipper} from "../../services/model/ClipperModel";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import PropTypes from "prop-types";

const DeleteModal = (props) => {

    const [open, setOpen] = React.useState(false);
    const clipper: IClipper = props.clipperProp;
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    return (
        <div className="">
            {/* The button to open modal */}
            <label htmlFor="deleteModal" className="btn text-white">
                <FontAwesomeIcon icon={faTrashCan}/>
            </label>
            <input type="checkbox" id="deleteModal" className="modal-toggle"/>
            <label htmlFor="deleteModal" className="modal cursor-pointer">
                <label className="modal-box relative" htmlFor="">
                    <h3 className="font-bold text-lg">Are you sure you want to delete {clipper.name}?</h3>
                    <p className="py-4">This action cannot be undone! All collected clippers using this will be delete
                        subsequently.</p>
                    <div className="modal-action">
                        <label htmlFor="deleteModal" className="btn" onClick={() => props.deleteClipper()}>Yes</label>
                        <label htmlFor="deleteModal" className="btn">No!</label>
                    </div>
                </label>
            </label>
        </div>
    );
}

DeleteModal.propTypes = {
    clipperProp: PropTypes.object.isRequired,
    deleteClipper: PropTypes.func.isRequired
}

export default DeleteModal;
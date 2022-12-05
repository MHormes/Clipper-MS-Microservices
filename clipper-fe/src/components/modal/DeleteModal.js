import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import DeleteIcon from "@mui/icons-material/Delete";
import {IconButton} from "@mui/material";
import {useEffect} from "react";
import type {IClipper} from "../../services/model/ClipperModel";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

const DeleteModal = ({clipperProp, deleteClipper}) => {

    const [open, setOpen] = React.useState(false);
    const clipper: IClipper = clipperProp;
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);



    return (
        <div>
            <IconButton aria-label="delete" size="large" onClick={() => handleOpen()}>
                <DeleteIcon/>
            </IconButton>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        Are you sure you want to delete {clipper.name}?
                    </Typography>
                    <Button onClick={() => deleteClipper(clipper.id)}  sx={{ mt: 2 }}>Yes</Button>
                </Box>
            </Modal>
        </div>
    );
}

export default DeleteModal;
import {Axios} from "axios";

const ClipperApi = () => {

    const getAllClippers = () => {
        Axios.get("http://localhost:8080/api/clipper/all")
    }
}

export default ClipperApi
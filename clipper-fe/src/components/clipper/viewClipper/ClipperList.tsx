import React, {useEffect, useState} from "react";
import ClipperListItem from "./ClipperListItem";
import type {IClipper} from "../../../services/model/ClipperModel";
import LoadingSpinner from "../../siteDefaults/LoadingSpinner";
import PropTypes, {bool} from "prop-types";

const debug = false;
const ClipperList = (props) => {

    const [clipperList, setClipperList] = useState([] as IClipper[]);

    useEffect(() => {
        async function loadClipperList() {
            setClipperList(props.clipperListProp);
            if (debug) console.log(props.clipperListProp);
        }

        loadClipperList().then(r => {
            if (debug) console.log("Clipper list assigned!")
        });
    }, [props.clipperListProp])

        return (
            <>
                <div className="grid grid-cols-1 overflow-y-auto">
                    <ul>
                        {clipperList != null ?
                        clipperList.map(
                            (clipper) =>
                                (
                                    <li key={clipper.id}>
                                        <ClipperListItem clipper={clipper} seriesView={props.seriesView}/>
                                    </li>
                                ))
                            :
                            <LoadingSpinner/>
                        }
                    </ul>
                </div>
            </>
        )
}

ClipperList.propTypes = {
    seriesView: bool,
    clipperListProp: PropTypes.array
}

ClipperList.defaultProps = {
    seriesView: false
}
export default ClipperList;
import React, {useEffect, useState} from "react";
import ClipperSingle from "./ClipperSingle";
import type {IClipper} from "../../../services/model/ClipperModel";
import PropTypes from "prop-types";

const debug = false;
const ClipperList = (props) => {

    const [clipperList: IClipper[], setClipperList] = useState();

    useEffect(() => {
        async function loadClipperList() {
            setClipperList(props.clipperListProp);
            if (debug) console.log(props.clipperListProp);
        }

        loadClipperList().then(r => {
            if (debug) console.log("Clipper list assigned!")
        });
    }, [props.clipperListProp])

    if (clipperList != null)
        return (
            <>
                <div className="grid grid-cols-1 overflow-y-auto">
                    <ul>
                        {clipperList.map(
                            (clipper) =>
                                (
                                    <li key={clipper.id}>
                                        <ClipperSingle clipper={clipper} seriesView={props.seriesView}/>
                                    </li>
                                ))}
                    </ul>
                </div>
            </>
        )
}

ClipperList.propTypes = {
    clipperListProp: PropTypes.array.isRequired
}

ClipperList.defaultProps = {
    seriesView: false
}
export default ClipperList;
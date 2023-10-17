import React, {useContext} from "react";
import {useNavigate} from "react-router";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBars} from "@fortawesome/free-solid-svg-icons";
import {AuthContext} from "../../services/RouteAuthProvider";


//todo refactor away from navbar component
const pages = ['Clippers', 'Series'];
const appName = 'ClipperMS';

const Navbar = () => {
    const navigate = useNavigate();

    const { onLogout } = useContext(AuthContext);

    const handleCloseNavMenu = (chosenMenuOption) => {
        //todo improve chosen menu option handling & navbar closing
        if (chosenMenuOption != null) {
            navigate(chosenMenuOption.toLowerCase());
        }
    };

    return (
        // todo create a better navbar
        <div className="navbar bg-base-100">
            <div className="navbar-start">
                <div className="dropdown">
                    <label tabIndex={0} className="btn btn-ghost lg:hidden">
                        <FontAwesomeIcon icon={faBars}/>
                    </label>
                    <ul tabIndex={0}
                        className="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
                        {pages.map((page) => (
                            <li key={page}>
                                <button onClick={() => handleCloseNavMenu(page)} className="menu-item">
                                    {page}
                                </button>
                            </li>
                        ))}
                        {localStorage.getItem('token') ? (
                            <li>
                                <button onClick={onLogout} className="menu-item">
                                    Logout
                                </button>
                            </li>
                        ) : null}
                    </ul>
                </div>
                <button className="btn btn-ghost normal-case text-xl" onClick={() => navigate("/")}>{appName}</button>
            </div>
            <div className="navbar-center hidden lg:flex">
                <ul className="menu menu-horizontal p-0">
                    {pages.map((page) => (
                        <li key={page}>
                            <button onClick={() => handleCloseNavMenu(page)} className="menu-item">
                                {page}
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default Navbar;
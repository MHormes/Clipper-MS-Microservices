import React, { useContext } from "react";
import { useNavigate } from "react-router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { AuthContext } from "../../services/security/RouteAuthProvider";

const pages = ['Clippers', 'Series'];
const appName = 'ClipperMS';

const Navbar = () => {
    const navigate = useNavigate();

    const { isAuthenticated, logout } = useContext(AuthContext);

    const handleCloseNavMenu = (chosenMenuOption) => {
        if (chosenMenuOption != null) {
            navigate(chosenMenuOption.toLowerCase());
        }
    };

    const handleLogout = () => {
        logout();
    };

    return (
        <div className="navbar bg-base-100">
            <div className="navbar-start">
                <div className="dropdown">
                    <label tabIndex={0} className="btn btn-ghost lg:hidden">
                        <FontAwesomeIcon icon={faBars}/>
                    </label>
                    <ul tabIndex={0} className="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
                        {pages.map((page) => (
                            <li key={page}>
                                <button onClick={() => handleCloseNavMenu(page)} className="menu-item">
                                    {page}
                                </button>
                            </li>
                        ))}
                        {isAuthenticated && (
                            <li>
                                <button onClick={handleLogout} className="menu-item">
                                    Logout
                                </button>
                            </li>
                        )}
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
                    {isAuthenticated && (
                        <li>
                            <button onClick={handleLogout} className="menu-item">
                                Logout
                            </button>
                        </li>
                    )}
                </ul>
            </div>
        </div>
    );
};

export default Navbar;

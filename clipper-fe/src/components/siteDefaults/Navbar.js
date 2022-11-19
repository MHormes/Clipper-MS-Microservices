import React, {useState, MouseEvent} from "react";
import {
    AppBar,
    Container,
    Toolbar,
    IconButton,
    Typography,
    Box,
    Menu,
    Avatar,
    Button,
    Tooltip,
    MenuItem
} from "@mui/material";


import MenuIcon from '@mui/icons-material/Menu';
import AdbIcon from '@mui/icons-material/Adb';
import {useNavigate} from "react-router";

//todo refactor away from navbar component
const pages = ['Clippers', 'Series', 'Login'];
const settings = ['Profile', 'Logout'];

const appName = 'ClipperMS';

const Navbar = () => {
    const navigate = useNavigate();

    const [anchorElNav, setAnchorElNav] = useState(null);
    const [anchorElUser, setAnchorElUser] = useState(null);

    const handleOpenNavMenu = (event: MouseEvent<HTMLElement>) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseNavMenu = (chosenMenuOption) => {
        setAnchorElNav(null);
        //todo improve chosen menu option handling
        if (chosenMenuOption != null) {
            console.log(chosenMenuOption);
            navigate(chosenMenuOption);
        }
    };

    const handleCloseUserMenu = (chosenSettingsOption) => {
        setAnchorElUser(null);
        if (chosenSettingsOption != null) {
            console.log(chosenSettingsOption);
            navigate(chosenSettingsOption);
        }
    };

    return (
        // todo refactor to remove unnecessary options included in material ui
        <AppBar position="static"
                sx={{
                    backgroundColor: 'black',
                }}>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    {/*region NAVBAR BIG SCREEN*/}
                    {/*TITLE OF NAVBAR*/}
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        sx={{
                            mr: 2,
                            display: {xs: 'none', md: 'flex'},
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'inherit',
                            textDecoration: 'none',
                        }}
                    >
                        {appName}
                    </Typography>
                    {/*CONTAINER OF NAVBAR MENU*/}
                    <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
                        <IconButton
                            size="large"
                            aria-label="account of current user"
                            aria-controls="menu-appbar"
                            aria-haspopup="true"
                            onClick={handleOpenNavMenu}
                            color="inherit"
                        >
                            <MenuIcon/>
                        </IconButton>
                        {/*MENU OF NAVBAR*/}
                        <Menu
                            id="menu-appbar"
                            anchorEl={anchorElNav}
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'left',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'left',
                            }}
                            open={Boolean(anchorElNav)}
                            onClose={handleCloseNavMenu}
                            sx={{
                                display: {xs: 'block', md: 'none'},
                            }}
                        >

                            {/*MENU ITEMS OF NAVBAR MENU*/}
                            {pages.map((page) => (
                                <MenuItem key={page} onClick={() => handleCloseNavMenu(page)}>
                                    <Typography textAlign="center">{page}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Box>
                    {/*endregion*/}
                    {/*region NAVBAR SMALL SCREEN*/}
                    {/*TITLE OF NAVBAR*/}
                    <Typography
                        variant="h5"
                        noWrap
                        component="a"
                        href=""
                        sx={{
                            mr: 2,
                            display: {xs: 'flex', md: 'none'},
                            flexGrow: 1,
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'inherit',
                            textDecoration: 'none',
                        }}
                    >
                        {appName}
                    </Typography>
                    {/*MENU OF NAVBAR*/}
                    <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                        {/*MENU ITEMS OF NAVBAR MENU*/}
                        {pages.map((page) => (
                            <Button
                                key={page}
                                onClick={() => handleCloseNavMenu(page)}
                                sx={{my: 2, color: 'white', display: 'block'}}
                            >
                                {page}
                            </Button>
                        ))}
                    </Box>
                    {/*endregion*/}
                    {/*region USER MENU*/}
                    <Box sx={{flexGrow: 0}}>
                        <Tooltip title="Open settings">
                            <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                                <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg"/>
                            </IconButton>
                        </Tooltip>
                        <Menu
                            sx={{mt: '45px'}}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >
                            {settings.map((setting) => (
                                <MenuItem key={setting} onClick={() => handleCloseUserMenu(setting)}>
                                    <Typography textAlign="center">{setting}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Box>
                    {/*endregion*/}
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default Navbar;
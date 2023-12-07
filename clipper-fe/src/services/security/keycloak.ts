import Keycloak from "keycloak-js";


// @ts-ignore
const clientId = process.env.REACT_APP_KEYCLOAK_CLIENT_ID;
// @ts-ignore
const realm = process.env.REACT_APP_KEYCLOAK_REALM;
// @ts-ignore
const url = process.env.REACT_APP_KEYCLOAK_URL;
// @ts-ignore
const redirectUrlLogin = process.env.REACT_APP_KEYCLOAK_LOGIN_URL;
// @ts-ignore
const redirectUrlLogout = process.env.REACT_APP_KEYCLOAK_LOGOUT_URL;

const initOptions = {
    realm: realm,
    clientId: clientId,
    url: url,
}

const keycloak = new Keycloak(initOptions);


function log(message: string) {
    const debug = false; // This should probably be set as a class property or external configuration
    if (debug) {
        console.log(`[Flowcontrol]_[Keycloak] - ${message} `);
    }
}


async function Init() {
    console.log("Keycloak init")
    await keycloak
        .init(
            {
                onLoad: "check-sso",
                redirectUri: redirectUrlLogin,
                pkceMethod: "S256",
                checkLoginIframe: false,
            }
        )
        .then(async (auth) => {
            log(`keycloak authenticated: ${auth}`)
            if (auth) {
                await keycloak.loadUserProfile().then(
                    (profile) => {
                        log(`keycloak profile logged in as: ${profile.email}`)
                        log(`access token ${keycloak.token}`)
                    }
                )
            }else{
                log("Keycloak not authenticated")
            }
        });
    log("Keycloak authenticated")
    setInterval(() => {
        keycloak
            .updateToken(70)
            .then((refreshed) => {
                if (refreshed) {
                    localStorage.setItem("access_token", keycloak.idToken as string);
                    log("Token refreshed" + refreshed);
                } else {
                    if (keycloak.tokenParsed && typeof keycloak.tokenParsed.exp === 'number' && typeof keycloak.timeSkew === 'number') {
                        // console.warn(
                        //     "Token not refreshed, valid for " +
                        //     Math.round(
                        //         keycloak.tokenParsed.exp +
                        //         keycloak.timeSkew -
                        //         new Date().getTime() / 1000
                        //     ) +
                        //     " seconds"
                        // );
                    } else {
                        console.warn("Token details or time skew are not available.");
                    }
                }
            })
            .catch(() => {
                console.error("Failed to refresh token");
            });
    }, 3000);
    log("Keycloak initialized");
}

const capitalizeFirstLetter = (str: string | undefined): string | undefined => {
    if (!str) return undefined;
    return str.charAt(0).toUpperCase() + str.slice(1);
};
const UserName = (): string | undefined => {
    const {firstName, lastName} = keycloak?.profile ?? {};

    const capitalizedFirstName = capitalizeFirstLetter(firstName);
    const capitalizedLastName = capitalizeFirstLetter(lastName);

    if (capitalizedFirstName || capitalizedLastName) {
        return `${capitalizedFirstName} ${capitalizedLastName}`.trim();
    }

    return undefined;
}

const UserId = (): string | undefined => {
    return keycloak?.profile?.id ?? keycloak?.tokenParsed?.sub;
};


const Token = (): string | undefined => keycloak?.token;
const RefreshToken = (): string | undefined => keycloak?.refreshToken;

const LogOut = () => keycloak.logout({
    redirectUri: redirectUrlLogout,
});

const UserRoles = (): string[] | undefined => {
    if (keycloak.realmAccess === undefined) return undefined;
    if (keycloak.realmAccess.roles === undefined) return undefined;

    return keycloak.realmAccess.roles
        .map((role: string) => role.toLowerCase())
        .filter((role: string) => role.startsWith("role"))
        .map((role: string) => role.charAt(0).toUpperCase() + role.slice(1));
};

const UserHasRole = (role: string): boolean => {
    if (keycloak.realmAccess === undefined) return false;
    if (keycloak.realmAccess.roles === undefined) return false;

    return keycloak.realmAccess.roles.indexOf(role) > -1;
}

const updateToken = (successCallback: any) =>
    keycloak.updateToken(5).then(successCallback).catch(doLogin);

const doLogin = keycloak.login;

const isLoggedIn = () => !!keycloak.token;

const KeycloakService = {
    CallInit: Init,
    CallLogin: doLogin,
    GetUserId: UserId,
    GetUserName: UserName,
    GetAccessToken: Token,
    GetRefreshToken: RefreshToken,
    CallLogOut: LogOut,
    GetUserRoles: UserRoles,
    UpdateToken: updateToken,
    IsLoggedIn: isLoggedIn,
    UserHasRole: UserHasRole
};

export default KeycloakService;

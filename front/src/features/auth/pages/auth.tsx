import {Outlet} from "react-router-dom";


function auth() {
    return (
        <div>
            <div>Auth</div>
            <Outlet />
        </div>
    )
}

export default auth;
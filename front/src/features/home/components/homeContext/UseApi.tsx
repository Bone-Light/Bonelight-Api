import {Outlet} from "react-router-dom";

function UseApi(){
    return (
        <>
            <h1>Api 接口调用</h1>
            <Outlet/>
        </>
    )
}

export default UseApi;
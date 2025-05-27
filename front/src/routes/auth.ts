import {lazy} from "react";

const authChildren = [
    {
        path: "login",
        Component: lazy(()=>import("@/features/auth/pages/Login.tsx"))
        // Component: login
    },
    {
        path:"forget",
        Component: lazy(()=>import("@/features/auth/pages/forget"))
    },
    {
        path:"register",
        Component: lazy(()=>import("@/features/auth/pages/register"))
    }
]

export default authChildren;
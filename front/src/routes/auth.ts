import {lazy} from "react";

const authChildren = [
    {
        path: "login",
        Component: lazy(()=>import("@/features/auth/pages/Login.tsx"))
        // Component: login
    },
    {
        path:"forget",
        Component: lazy(()=>import("@/features/auth/pages/Forget.tsx"))
    },
    {
        path:"register",
        Component: lazy(()=>import("@/features/auth/pages/Register.tsx"))
    }
]

export default authChildren;
import {createBrowserRouter} from "react-router-dom";
import auth from "@/features/auth/pages/auth.tsx";
import authChildren from "@/routes/auth.ts";
const router = createBrowserRouter([
    {
        path: "/auth",
        Component: auth,
        children: authChildren
    }
])

export default router;
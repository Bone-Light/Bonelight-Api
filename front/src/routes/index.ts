import {createBrowserRouter} from "react-router-dom";
import auth from "@/features/auth/pages/auth.tsx";
import authChildren from "@/routes/auth.ts";
import home from "@/features/home/pages/home.tsx";
const router = createBrowserRouter([
    {
      path:"",
      Component: home,
    },
    {
        path: "/auth",
        Component: auth,
        children: authChildren
    }
])

export default router;
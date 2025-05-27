import './App.css'
import {RouterProvider} from "react-router-dom";
import router from "@/routes"
import {useEffect} from "react";
import {useAppDispatch, useUser} from "@/store/hooks.ts";
import {autoLogin} from "@/store/slice/authSlice.ts";

function App() {
  const { auth } = useUser()
  const dispatch = useAppDispatch()

  useEffect(() => {
    // 触发自动登录
    dispatch(autoLogin())
  }, [dispatch])   // [dispatch] 变化会重新运行，不过这里写是因为规范

  if (auth.loading) {
    // return <div>Loading...</div>
    console.log("Loading autologin...")
  }

  return (
    <RouterProvider router={router}/>
  )
}
export default App

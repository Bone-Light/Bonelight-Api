import './App.css'
import {useEffect} from "react";
import {useAppDispatch, useUser} from "@/store/hooks.ts";
import {autoLogin} from "@/store/slice/authSlice.ts";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "@/features/home/pages/Home.tsx";
import Auth from "@/features/auth/pages/auth.tsx";
import Login from "@/features/auth/pages/Login.tsx";
import Register from "@/features/auth/pages/Register.tsx";
import Forget from "@/features/auth/pages/Forget.tsx";
import User from "@/features/auth/pages/User.tsx";
import Nav1 from "@/features/home/components/homeContext/Nav1.tsx";
import Setting from "@/features/setting/pages/Setting.tsx";

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
      <BrowserRouter>
        <Routes>
          <Route path="/auth" element={<Auth/>}>
            <Route path="login" element={<Login/>}/>
            <Route path="register" element={<Register/>}/>
            <Route path="forget" element={<Forget/>}/>
          </Route>

          <Route path="/" element={<Home/>}>
            <Route path="A" element={<Nav1/>}>
              <Route path=":userId" element={<User/>}/>
            </Route>

            <Route path="Setting" element={<Setting/>}>
              <Route path=":userId" element={<User/>}/>
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
  )
}
export default App

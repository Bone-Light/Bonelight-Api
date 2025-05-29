import {Link} from "react-router-dom";

function forget(){
    return (
        <div>
            <div>忘记密码</div>
            <Link to={'/auth/login'} style={{fontSize:"smaller"}}>已有账号, 立即登录</Link>
        </div>
    )
}
export default forget;
import {Button, Card, Divider, Form, type FormInstance, type FormRule, Input} from "antd";
import {useRef, useState} from "react";
import {Link} from "react-router-dom";
import TimePlay from "@/Utils/TimePlayUtil.ts";
import {useForm} from "antd/es/form/Form";

function Register() {
    const [loading, setLoading]= useState(false);
    const load = ()=>setLoading(prev=>!prev);
    const [formRef] = useForm();
    const register = ()=>{
        setLoading(true);
        TimePlay.addTask(load,500,1);

    }

    const [cd,setCd] = useState(0)
    const updateCd = ()=>{
        setCd(prev => prev-1);
    }
    const getCode = (count:number)=> {
        if(cd<=0) setCd(count);
        TimePlay.addTask(updateCd, 1000, count);
    }

    const validatePassword = ({ getFieldValue }:{ getFieldValue: FormInstance['getFieldValue'] }) => ({
        validator(_:FormRule, value:unknown) {
            if (value && getFieldValue('password') !== value) {
                return Promise.reject('两次密码不一致');
            }
            return Promise.resolve();
        }
    });


    return(
        <>
            <Card style={{width: "600px", height: "400px", margin: "auto"}}>
                <h2 style={{margin: 0, padding: 0}}>加入我们</h2>
                <Link to={'/auth/login'} style={{fontSize:"smaller"}}>已有账号, 立即登录</Link>
                <Divider/>
                <Form
                    form={formRef}
                    name="registerForm"
                    labelCol={{ span: 6 }}
                    wrapperCol={{ span: 14 }}
                >
                    <Form.Item label="用户名" name="username" rules={[{required: true, message: '必填项'}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item label="电子邮箱" name="email" rules={[{required: true, message: '必填项'}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item label="密码" name="password" rules={[{required: true, message: '必填项'}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item rules={[validatePassword,{ required: true, message: '必填项' }]}
                               dependencies={["password"]}
                               label="确定密码" name="confirmPassword">
                        <Input/>
                    </Form.Item>
                    <Form.Item label="验证码" name="code" rules={[{required: true, message: '必填项'}]}>
                        <div style={{display: "flex", gap: "8px"}}>
                            <Input style={{flex: 1}}/>
                            {cd <= 0?(<Button onClick={()=>getCode(60)}>获取验证码</Button>):
                                  (<Button onClick={()=>{setCd(prev=>prev-1)}} disabled={cd>0}>请等待 {cd} 秒</Button>)}
                        </div>
                    </Form.Item>
                    <Form.Item label={null}>
                        <Button style={{width: "80%"}} onClick={register} loading={loading} htmlType={"submit"}>注册</Button>
                    </Form.Item>
                </Form>
            </Card>
        </>
    )
}

export default Register
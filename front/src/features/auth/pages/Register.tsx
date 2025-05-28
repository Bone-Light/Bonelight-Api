import {Button, Card, Divider, Form, type FormInstance, type FormRule, Input} from "antd";
import {useState} from "react";

function Register() {
    const [a, setA] = useState(true);
    const ca = ()=>{setA(!a)}

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
            <Card style={{width: "600px", height: "400px",margin: "auto"}}>
                <h2>加入我们</h2>
                <Divider/>
                <Form
                    name="registerForm"
                    labelCol={{ span: 6 }}
                    wrapperCol={{ span: 14 }}
                >
                    <Form.Item label="电子邮箱" name="email" rules={[{required: true, message: '必填项'}]} required>
                        <Input/>
                    </Form.Item>
                    <Form.Item label="密码" name="password" rules={[{required: true, message: '必填项'}]} required>
                        <Input/>
                    </Form.Item>
                    <Form.Item rules={[validatePassword,{ required: true, message: '必填项' }]}
                               label="确定密码" name="confirmPassword" required>
                        <Input/>
                    </Form.Item>
                    <Form.Item label="验证码" name="code" rules={[{required: true, message: '必填项'}]} required>
                        <div style={{display: "flex", gap: "8px"}}>
                            <Input style={{flex: 1}}/>
                            {a?(<Button onClick={ca}>获取验证码</Button>):
                                  (<Button onClick={ca}>请等待 60 秒</Button>)}
                        </div>
                    </Form.Item>
                    <Form.Item label={null}>
                        <Button htmlType={"submit"}>注册</Button>
                    </Form.Item>
                </Form>
            </Card>
        </>
    )
}

export default Register
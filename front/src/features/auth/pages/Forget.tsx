import {Link} from "react-router-dom";
import {Button, Card, Col, Divider, Form, type FormInstance, type FormRule, Input, Row} from "antd";
import {useForm} from "antd/es/form/Form";

function Forget(){
    const [formRef] = useForm();

    const validatePassword = ({ getFieldValue }:{ getFieldValue: FormInstance['getFieldValue'] })=>({
        validator(_:FormRule, value:unknown) {
            if (value && getFieldValue('password') !== value) {
                return Promise.reject('两次密码不一致');
            }
            return Promise.resolve();
        }
    }); // ()=>({}) 工厂函数的写法，用于动态生成规则对象

    return (
        <>
            <Card style={{ width: '400px', height: '400px', boxShadow: 'inset 0 1px 6px rgba(0,0,0,0.23)' }}>
                <h2>忘记密码</h2>
                <Divider/>
                <Form
                    form={formRef}
                    labelCol={{span: 6}}
                    wrapperCol={{span: 16}}
                >
                    <Form.Item label="电子邮箱" name="email"
                               rules={[
                                   {pattern:/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message:"请填写正确的邮箱地址"}
                               ]}
                               required={true}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item label="新密码" name="password" rules={[{required:true, message:'必填项'},{pattern:/^[A-Za-z\d!@#$%^&*()_+-=]{6,18}/, message:"要求6-18位，且不包含非法符号"}]}>
                        <Input type="password"/>
                    </Form.Item>

                    <Form.Item label="确定密码" name="confirmPassword"
                        dependencies={['password']}
                        rules={[validatePassword, {required: true, message: '必填项'}]}
                    >
                        <Input type="password"/>
                    </Form.Item>

                    <Form.Item label="验证码" name="code" rules={[{required: true, message: '必填项'},{pattern:/^[0-9]{6}$/, message:"请填写6位验证码"}]}>
                        <Row gutter={8}>
                            <Col span={14}><Input/></Col>
                            <Col span={4}><Button>获取验证码</Button></Col>
                        </Row>
                    </Form.Item>

                    <Form.Item label={null}>
                        <Button htmlType={'submit'} style={{width: '80%'}}>重置密码</Button>
                    </Form.Item>
                </Form>
            </Card>


            <Link to={'/auth/login'} style={{fontSize:"smaller"}}>已有账号, 立即登录</Link>

        </>
    )
}
export default Forget;
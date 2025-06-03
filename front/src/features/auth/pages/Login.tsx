// src/pages/Login.tsx
import {Form, Input, Button, Checkbox, Card, Alert, Col, Row} from 'antd';

import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {Link, useNavigate} from 'react-router-dom';
import {useUser} from '@/store/hooks';

function Login(){
    const { auth } = useUser();
    const navigate = useNavigate();
    // const dispatch = useAppDispatch();

    // 如果已登录则跳转
    // React.useEffect(() => {
    //     if (auth.user) {
    //         navigate('/'); // 替换为你的主页路由
    //     }
    // }, [auth.user, navigate]);

    const onFinish = async (values: {
        body:{
            username: string;
            password: string;
        }
        rememberMe: boolean;
    }) => {
        try {
            navigate('/')
            // await dispatch(loginUser(values));
        } catch (err) {
            console.error('Login failed:', err);
        }
    };

    return (
        <div>
            <Card style={{width: "600px", height: "440px", margin: "auto", boxShadow: 'inset 0 1px 6px rgba(0,0,0,0.23)' }}>
                <h2>系统登录</h2>

                {auth.error && (
                    <Alert
                        message={auth.error}
                        type="error"
                        showIcon
                        closable
                        style={{ marginBottom: 24 }}
                    />
                )}

                <Form
                    name="login"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    size="large"
                >
                    <Form.Item
                        name="username"
                        rules={[{ required: true, message: '请输入用户名!' }]}
                    >
                        <Input
                            prefix={<UserOutlined />}
                            placeholder="用户名"
                            size="large"
                        />
                    </Form.Item>

                    <Form.Item
                        name="password"
                        rules={[{ required: true, message: '请输入密码!' }]}
                    >
                        <Input.Password
                            prefix={<LockOutlined />}
                            placeholder="密码"
                            size="large"
                        />
                    </Form.Item>

                    <Form.Item name="remember" valuePropName={'checked'}>
                        <Checkbox>记住登录状态</Checkbox>
                    </Form.Item>

                    <Form.Item>
                        <Row gutter={[16, 16]}>
                            <Col span={12}>
                                <Button
                                    type="primary"
                                    htmlType="submit"
                                    loading={auth.loading}
                                    block
                                    size="large"
                                >
                                    登录
                                </Button>
                            </Col>
                            <Col span={12}>
                                <Button
                                    type="default"
                                    block
                                    size="large"
                                    onClick={()=>navigate('/auth/forget')}
                                >
                                    忘记密码
                                </Button>
                            </Col>
                        </Row>
                    </Form.Item>
                </Form>
                <Link to={'/auth/register'} style={{fontSize:"smaller"}}>没有账号, 立即注册</Link>
            </Card>
        </div>
    );
}
export default Login;
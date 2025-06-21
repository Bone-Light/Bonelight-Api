import { Avatar, Button, Card, Input, List, Tag, Space, Typography } from "antd";
import type { ApiObj } from "@/Api/type/ApiObj.ts";
import { TwitterOutlined, SearchOutlined } from "@ant-design/icons";
import {useNavigate} from "react-router-dom";
import {useAppDispatch, useUser} from "@/store/hooks.ts";
import {getApiList} from "@/store/slice/apiListSlice.ts";
import {useEffect, useState} from "react";

const { Title, Paragraph } = Typography;

function ApiGround() {
    const {apiList} = useUser();

    const [list, setList] = useState<ApiObj[]>([]);
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    // 在 useEffect 中触发异步状态更新
    useEffect(() => {
        dispatch(getApiList()); // ✅ 渲染完成后执行
    }, [dispatch]); // 依赖项为 dispatch

    useEffect(() => {
        setList(apiList.apiList);
    }, [apiList]);

    return (
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
            <Card
                style={{
                    background: 'linear-gradient(135deg, #1a1a1a 0%, #373737 100%)',
                    borderRadius: '12px',
                    boxShadow: '0 4px 12px rgba(0,0,0,0.1)'
                }}
            >
                <Title level={2} style={{ color: 'white', margin: 0 }}>接口广场</Title>
                <Paragraph style={{ color: '#e0e0e0', marginBottom: 0 }}>调用接口，连接世界</Paragraph>
            </Card>

            <Card
                style={{
                    borderRadius: '8px',
                    boxShadow: '0 2px 8px rgba(0,0,0,0.05)'
                }}
            >
                <Space.Compact style={{ width: '100%', justifyContent: 'center' }}>
                    <Input
                        placeholder="请输入要查找的Api接口"
                        style={{ maxWidth: '500px' }}
                        prefix={<SearchOutlined style={{ color: '#bfbfbf' }} />}
                    />
                    <Button type="primary">查询</Button>
                </Space.Compact>
            </Card>

            <Card
                style={{
                    borderRadius: '8px',
                    boxShadow: '0 2px 8px rgba(0,0,0,0.05)'
                }}
            >
                <List
                    header={
                        <Title level={4} style={{ margin: 0 }}>Api 合集</Title>
                    }
                    bordered
                    dataSource={list}
                    renderItem={(item: ApiObj) => (
                        <List.Item>
                            <Card
                                hoverable
                                style={{
                                    width: "100%",
                                    borderRadius: '6px'
                                }}
                                onClick={(e)=> {
                                    e.stopPropagation();
                                    navigate(`UseApi/${item.id ?? 'error'}`)}
                                }
                            >
                                <Space size="middle" align="center">
                                    <Avatar
                                        src={item.avatar}
                                        size="large"
                                        style={{ boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}
                                    />
                                    <Space direction="vertical" size="small">
                                        <Space>
                                            <Title level={5} style={{ margin: 0 }}>{item.apiName}</Title>
                                            <Tag
                                                icon={<TwitterOutlined />}
                                                color="#55acee"
                                                style={{ borderRadius: '4px' }}
                                            >
                                                Twitter
                                            </Tag>
                                        </Space>
                                        <Paragraph style={{ margin: 0, color: '#8a8989' }}>
                                            {item.description}
                                        </Paragraph>
                                    </Space>
                                </Space>
                            </Card>
                        </List.Item>
                    )}
                />
            </Card>
        </Space>
    );
}

export default ApiGround;
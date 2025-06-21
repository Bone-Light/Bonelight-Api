import {Card, Col, Divider, Row, Table, Tabs} from "antd";
import {useAppDispatch, useUser} from "@/store/hooks.ts";
import {useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {getApiList} from "@/store/slice/apiListSlice.ts";
import type {ApiObj} from "@/Api/type/ApiObj.ts";
import {BarsOutlined, BookOutlined, BugOutlined, CodeOutlined, CopyOutlined} from "@ant-design/icons";
import Title from "antd/es/skeleton/Title";
import ApiFile from "../UseApiComponents/ApiFile";
import FetchApi from "@/features/home/components/UseApiComponents/FetchApi.tsx";

function UseApi(){
    const {apiList} = useUser();
    const {apiId} = useParams();
    const dispatch = useAppDispatch();
    const [Api, setApi] = useState<ApiObj|undefined>(undefined);

    const tabList = [
        {
            key: 'tab1',
            tab: 'tab1',
        },
        {
            key: 'tab2',
            tab: 'tab2',
        },
        {
            key: 'tab3',
            tab: 'tab3',
        },
        {
            key: 'tab4',
            tab: 'tab4',
        }
    ]

    const contentList: Record<string, React.ReactNode> = {
        tab1: <ApiFile Api={Api}/>,
        tab2: <FetchApi/>,
        tab3: <p>content3</p>,
        tab4: <p>content4</p>,
    };

    const [tableKey, setTableKey] = useState<string>('tab1');


    // 在 useEffect 中触发异步状态更新
    useEffect(() => {
        dispatch(getApiList()); // ✅ 渲染完成后执行
    }, [dispatch]); // 依赖项为 dispatch

    useEffect(() => {
        setApi(apiList.apiList.find((item) => item.id?.toString() === apiId));
    }, [apiList, apiId]);

    return (
        <>
            <Card>
                <h2>{Api?.apiName} 接口调用</h2>
                <Divider />
                <Row gutter={8} justify="space-around">
                    <Col span={8}>接口地址: </Col>
                    <Col span={8}>返回格式: </Col>
                    <Col span={8}>使用花销: </Col>
                </Row>
                <br/>
                <Row gutter={8} justify="space-around">
                    <Col span={8}>请求方式: </Col>
                    <Col span={8}>调用总次数: </Col>
                    <Col span={8}>接口状态: </Col>
                </Row>
                <br/>
                <Row justify="space-around">
                    <Col span={8}>接口描述: </Col>
                    <Col span={8}>请求实例: </Col>
                </Row>
                <Divider />
                <div>
                    <h3>更多消息在开发者文档查看</h3>
                </div>
            </Card>

            <br/>
            <Card
                style={{width:'100%'}}
                title="Card title"
                tabList={tabList}
                activeTabKey={tableKey}
                onTabChange={(key) => {setTableKey(key)}}
            >
                {contentList[tableKey]}
            </Card>
        </>
    )
}

export default UseApi
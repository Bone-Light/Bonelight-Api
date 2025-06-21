import {BarsOutlined} from "@ant-design/icons";
import type {ApiObj} from "@/Api/type/ApiObj.ts";
import {Table, type TableProps} from "antd";

interface ApiFileProps {
    Api?: ApiObj | undefined
}

interface RequestType {
    key: string;
    name: string;
    necessary: number;
    type: string;
    description: string;
}

interface ResponseType {
    key: string;
    name: string;
    type: string;
    description: string;
}

const requestColumns: TableProps<RequestType>['columns'] = [
    {
        title: '请求参数名称',
        dataIndex: 'name',
        key: 'name',
        render: (text) => text ?? "无"
    },
    {
        title: '必选',
        dataIndex: 'necessary',
        key: 'necessary',
        render: (text) => text ?? "无"
    },
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        render: (text) => text ?? "无"
    },
    {
        title: '描述',
        dataIndex: 'description',
        key: 'description',
        render: (text) => text ?? "无"
    },
]

const responseColumns: TableProps<ResponseType>['columns'] = [
    {
        title: '响应参数名称',
        dataIndex: 'name',
        key: 'name',
        render: (text) => text ?? "无"
    },
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        render: (text) => text ?? "无"
    },
    {
        title: '描述',
        dataIndex: 'description',
        key: 'description',
        render: (text) => text ?? "无"
    },
]

function ApiFile({Api}: ApiFileProps) {
    const requestData:RequestType[] = [

    ]
    return (
        <>
            <h2><BarsOutlined/> content1</h2>
            <Table<RequestType>
                columns={requestColumns}
                dataSource={requestData}
                locale={{
                    emptyText: "无请求参数"
                }}
            />
            <br/>
            <Table<ResponseType>
                columns={responseColumns}
                dataSource={[]}
                locale={{
                    emptyText: "无返回参数"
                }}
            />

        </>
    )
}

export default ApiFile;
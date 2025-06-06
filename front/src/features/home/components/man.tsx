// // eslint-disable-next-line @typescript-eslint/ban-ts-comment
// // @ts-expect-error
// function Man({bob}) {
//     return(
//         <>
//             {bob}
//         </>
//     )
// }
//
// export default Man;



import React, { useState } from 'react';
import {
    AppstoreOutlined,
    CalendarOutlined,
    LinkOutlined,
    MailOutlined, MenuFoldOutlined, MenuUnfoldOutlined,
    SettingOutlined,
} from '@ant-design/icons';
import {Button, Divider, Menu, Switch} from 'antd';
import type { GetProp, MenuProps } from 'antd';

type MenuTheme = GetProp<MenuProps, 'theme'>;

type MenuItem = GetProp<MenuProps, 'items'>[number];

const items: MenuItem[] = [
    {
        key: '1',
        icon: <MailOutlined />,
        label: 'Navigation One',
    },
    {
        type: 'divider',
        dashed: true,
    },
    {
        key: '2',
        icon: <CalendarOutlined />,
        label: 'Navigation Two',
    },
    {
        type: 'group',
        key: 'sub1',
        label: <div> <AppstoreOutlined /> Navigation Two </div>,
        children: [
            { key: '3', label: 'Option 3'},
            { key: '4', label: 'Option 4' },
            {
                key: 'sub1-2',
                label: 'Submenu',
                children: [
                    { key: '5', label: 'Option 5' },
                    { key: '6', label: 'Option 6' },
                ],
            },
        ],
    },
    {
        key: 'sub2',
        label: 'Navigation Three',
        icon: <SettingOutlined />,
        children: [
            { key: '7', label: 'Option 7' },
            { key: '8', label: 'Option 8' },
            { key: '9', label: 'Option 9' },
            { key: '10', label: 'Option 10' },
        ],
    },
    {
        key: 'link',
        icon: <LinkOutlined />,
        label: (
            <a href="https://ant.design" target="_blank" rel="noopener noreferrer">
                Ant Design
            </a>
        ),
    },
];

const Man: React.FC = ({bob}) => {
    const [mode, setMode] = useState<'vertical' | 'inline'>('inline');
    const [theme, setTheme] = useState<MenuTheme>('light');

    const changeMode = (value: boolean) => {
        setMode(value ? 'vertical' : 'inline');
    };

    const changeTheme = (value: boolean) => {
        setTheme(value ? 'dark' : 'light');
    };

    const [collapsed, setCollapsed] = useState(false);

    const toggleCollapsed = () => {
        setCollapsed(!collapsed);
    };

    return (
        <>
            <Button type="primary" onClick={toggleCollapsed} style={{ marginBottom: 16 }}>
                {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            </Button>

            <Switch onChange={changeMode} /> Change Mode
            <Divider type="vertical" />
            <Switch onChange={changeTheme} /> Change Style
            <br />
            <br />
            <Menu
                style={{ maxWidth: 256 }}
                defaultSelectedKeys={['1']}
                defaultOpenKeys={['sub1']}
                mode={mode}
                theme={theme}
                items={items}
                inlineCollapsed={collapsed}
            />
        </>
    );
};

export default Man;
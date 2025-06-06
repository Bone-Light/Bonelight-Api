import type { MenuProps } from 'antd';
import { Layout, Menu, theme } from 'antd';
import {Outlet, useLocation, useNavigate} from "react-router-dom";
import GlBreadcrumb from "@/features/common/components/GlBreadcrumb.tsx";
const { Header, Footer} = Layout;

function Home(){
    const navigate = useNavigate();
    const { pathname } = useLocation();
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    const navs: MenuProps['items'] =
        [
            {
              key: '/',
              label: 'Home',
              onClick: ()=>{navigate('/')},
            },
            ...['A', 'B', 'C'].map((key) => ({
              key: '/'+key,
              label: `nav ${key}`,
              onClick: () => {navigate(key)},
            })),
            {
              key: '/setting',
              label: '个人主页',
              onClick: ()=>{navigate('setting')},
            },
        ];

    return (
        <Layout style={{width:'100%'}}>
            <Header style={{ display: 'flex', alignItems: 'center' }}>
                <div className="demo-logo" />
              <Menu
                    theme="dark"
                    mode="horizontal"
                    // defaultSelectedKeys={[pathname]} // 这是默认路由
                    selectedKeys={[pathname]} // 这是动态路由
                    items={navs}
                    style={{ flex: 1, minWidth: 0 }}
                    multiple={true}
                    onDeselect={({key})=> console.log(key)}
                />
            </Header>
            <div style={{ padding: '0 48px' }}>
                <GlBreadcrumb
                    style={{ margin: '16px 0' }}
                />
                <Outlet/>
            </div>
            <Footer style={{ textAlign: 'center' }}>
                Light-Api ©{new Date().getFullYear()} Created by BoneLight
            </Footer>
        </Layout>
    );
};

export default Home;
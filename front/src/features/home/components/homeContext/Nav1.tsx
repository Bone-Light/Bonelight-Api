import Sider from "antd/es/layout/Sider";
import {Layout, Menu, theme} from "antd";
import {Content} from "antd/es/layout/layout";
import {Outlet, useNavigate,} from "react-router-dom";
import Man from "@/features/home/components/man.tsx";

function Nav1() {
  const navigate = useNavigate();
  const {
    token: { colorBgContainer},
  } = theme.useToken();

  const gList = [
    ...['1', '2', '3'].map((key) => ({
      key: '/'+key,
      label: `${key}`,
      onClick: () => {navigate(key)},
    }))
  ];

  return (
    <Layout>
      <Sider style={{ background: colorBgContainer }} width={200}>
        <Menu
          mode="inline"
          items={gList}
        />
      </Sider>
      <Content style={{ padding: '0 24px', minHeight: 280 }}>
        <Man/>
        <Outlet/>
      </Content>
    </Layout>
    )
}
  export default Nav1

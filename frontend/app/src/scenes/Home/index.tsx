import React from 'react';
import { Avatar, Layout, Menu, Button } from 'antd';
const { Content, Sider, Header } = Layout;
import {
  UserOutlined,
  AreaChartOutlined,
  DownOutlined,
  BankOutlined,
} from '@ant-design/icons';
import { Switch, Link } from 'react-router-dom';
import RouteWithSubRoutes from '../../components/RouteWithSubRoutes';
import { SingleRoute } from '../../index';
import styles from './styles.css';
import logo from '../../../public/logo.png';

interface HomeProps {
  routes: SingleRoute[];
}

class Home extends React.Component<HomeProps> {
  render() {
    return (
      <Layout>
        <Sider
          style={{
            height: '100vh',
            position: 'fixed',
            zIndex: 2,
          }}
          breakpoint="lg"
          collapsedWidth="0"
        >
          <div>
            <Link to="/dashboard/home">
              <div className={styles.logo}>
                <img src={logo} alt="SEF Logo" />
              </div>
            </Link>
          </div>
          <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
            <Menu.Item key="1" icon={<AreaChartOutlined />}>
              <Link to="/dashboard/home/" className={styles.txtWhite}>
                Dashboard
              </Link>
            </Menu.Item>
            <Menu.Item key="2" icon={<BankOutlined />}>
              <Link to="/dashboard/universities/" className={styles.txtWhite}>
                Universities
              </Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout>
          <Header className={styles.header}>
            <Menu mode="horizontal" className={styles.rightAlignedMenu}>
              <Menu.SubMenu
                title={
                  <Button type="primary" shape="round">
                    Add <DownOutlined />
                  </Button>
                }
              >
                <Menu.Item>
                  <Link to="/dashboard/universities/add/">University</Link>
                </Menu.Item>
                <Menu.Item>
                  <Link to="/dashboard/ambassadors/add">Ambassador</Link>
                </Menu.Item>
              </Menu.SubMenu>
              <Menu.SubMenu
                title={
                  <Avatar className={styles.avatar} icon={<UserOutlined />} />
                }
              >
                <Menu.ItemGroup title={localStorage.username}>
                  <Menu.Item disabled={true}>Logout</Menu.Item>
                </Menu.ItemGroup>
              </Menu.SubMenu>
            </Menu>
          </Header>
          <Content className={styles.content}>
            <Switch>
              {this.props.routes.map((route) => (
                <RouteWithSubRoutes key={route.path} {...route} />
              ))}
            </Switch>
          </Content>
        </Layout>
      </Layout>
    );
  }
}

export default Home;

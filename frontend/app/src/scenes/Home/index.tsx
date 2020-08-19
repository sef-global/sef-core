import React from 'react';
import { Avatar, Layout, Menu, Button } from 'antd';
const { Content, Sider, Header } = Layout;
import {
  UserOutlined,
  AreaChartOutlined,
  DownOutlined,
  BankOutlined,
  GlobalOutlined,
  TeamOutlined,
  BookOutlined,
  FileAddOutlined,
  SolutionOutlined,
} from '@ant-design/icons';
import { Switch, Link } from 'react-router-dom';
import RouteWithSubRoutes from '../../components/RouteWithSubRoutes';
import styles from './styles.css';
import logo from '../../../public/logo.png';
import { HomeProps } from './interfaces';

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
          <Menu
            theme="dark"
            mode="inline"
            defaultOpenKeys={['multiverse', 'academix', 'fellowship']}
          >
            <Menu.Item key="dashboard" icon={<AreaChartOutlined />}>
              <Link to="/dashboard/home/" className={styles.txtWhite}>
                Dashboard
              </Link>
            </Menu.Item>
            <Menu.SubMenu
              key="multiverse"
              icon={<GlobalOutlined />}
              title="Multiverse"
            >
              <Menu.Item icon={<BankOutlined />}>
                <Link
                  key="multiverse_universities"
                  to="/dashboard/universities/"
                  className={styles.txtWhite}
                >
                  Universities
                </Link>
              </Menu.Item>
              <Menu.Item icon={<TeamOutlined />}>
                <Link
                  key="multiverse_ambassadors"
                  to="/dashboard/ambassadors/"
                  className={styles.txtWhite}
                >
                  Ambassadors
                </Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu
              key="academix"
              icon={<BookOutlined />}
              title="AcadeMix"
            >
              <Menu.Item icon={<FileAddOutlined />}>
                <Link
                  key="academix_categories"
                  to="/dashboard/academix/categories"
                  className={styles.txtWhite}
                >
                  Categories
                </Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu
              key="fellowship"
              icon={<BookOutlined />}
              title="Fellowship"
            >
              <Menu.Item icon={<SolutionOutlined />}>
                <Link
                  key="fellowship_certificates"
                  to="/dashboard/fellowship/certificates"
                  className={styles.txtWhite}
                >
                  Certificates
                </Link>
              </Menu.Item>
            </Menu.SubMenu>
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
                <Menu.ItemGroup title="Multiverse">
                  <Menu.Item key="add_university">
                    <Link to="/dashboard/universities/add/">University</Link>
                  </Menu.Item>
                  <Menu.Item key="add_ambassador">
                    <Link to="/dashboard/ambassadors/add">Ambassador</Link>
                  </Menu.Item>
                </Menu.ItemGroup>
                <Menu.ItemGroup title="Academix">
                  <Menu.Item key="add_item">
                    <Link to="/dashboard/academix/item/add">Item</Link>
                  </Menu.Item>
                  <Menu.Item key="add_sub_category">
                    <Link to="/dashboard/academix/sub-category/add">
                      Subcategory
                    </Link>
                  </Menu.Item>
                  <Menu.Item key="add_category">
                    <Link to="/dashboard/academix/category/add">Category</Link>
                  </Menu.Item>
                </Menu.ItemGroup>
                <Menu.ItemGroup title="Fellowship">
                  <Menu.Item key="add_item">
                    <Link to="/dashboard/fellowship/certificate/add">
                      Certificates
                    </Link>
                  </Menu.Item>
                </Menu.ItemGroup>
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

import React from 'react';
import {
  Form,
  Input,
  Button,
  Layout,
  Col,
  Row,
  Typography,
  notification,
} from 'antd';
import axios, { AxiosResponse } from 'axios';
import styles from './styles.css';

const { Content } = Layout;
const { Title, Text } = Typography;

interface LoginPayload {
  username: string;
}

interface LoginStateProps {
  isIncorrect: boolean;
}

class Login extends React.Component<any, LoginStateProps> {
  constructor(props: any) {
    super(props);
    this.state = {
      isIncorrect: false,
    };
  }

  login = (values: any) => {
    const data = `username=${values.username}&password=${values.password}`;
    axios
      .post(window.location.origin + '/core/login', data)
      .then((result: AxiosResponse<LoginPayload>) => {
        if (result.status === 200) {
          let redirectUrl = window.location.origin + '/dashboard';
          const searchParams = new URLSearchParams(window.location.search);
          const redirectPath = searchParams.get('redirect');
          if (redirectPath != undefined) {
            redirectUrl = redirectPath;
          }
          sessionStorage.setItem('sefdashboard_username', result.data.username);
          window.location.href = redirectUrl;
        }
      })
      .catch((error) => {
        if (error.hasOwnProperty('response') && error.response.status == 401) {
          this.setState({ isIncorrect: true });
        } else {
          notification.error({
            message: 'There was a problem',
            duration: 10,
            description: 'Error occured while trying to login',
          });
        }
      });
  };

  render() {
    return (
      <Content className={styles.content}>
        <Row>
          <Col xs={3} sm={3} md={8} />
          <Col xs={18} sm={18} md={6}>
            <Title className={styles.title}>Login</Title>
            <Form
              labelCol={{ span: 8 }}
              wrapperCol={{ span: 16 }}
              name="basic"
              initialValues={{ remember: true }}
              onFinish={this.login}
            >
              <Form.Item
                label="Username"
                name="username"
                rules={[
                  { required: true, message: 'Please input your username!' },
                ]}
              >
                <Input />
              </Form.Item>

              <Form.Item
                label="Password"
                name="password"
                rules={[
                  { required: true, message: 'Please input your password!' },
                ]}
              >
                <Input.Password />
              </Form.Item>
              {this.state.isIncorrect && (
                <Text type="danger">Invalid credentials</Text>
              )}
              <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type="primary" htmlType="submit">
                  Submit
                </Button>
              </Form.Item>
            </Form>
          </Col>
        </Row>
      </Content>
    );
  }
}

export default Login;

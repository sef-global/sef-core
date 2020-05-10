import React from 'react';
import {
  Col,
  Row,
  Typography,
  Form,
  Input,
  Button,
  notification,
  Spin,
} from 'antd';
import styles from '../../../../styles.css';
import axios, { AxiosResponse } from 'axios';
import { RouteComponentProps, withRouter } from 'react-router';
import { handleApiError } from '../../../../../../services/util/errorHandler';
import { University } from '../../index';

const { Title, Text } = Typography;
const layout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 },
};

interface AddUniversityStateProps {
  isLoading: boolean;
}

class AddUniversity extends React.Component<
  RouteComponentProps<any>,
  AddUniversityStateProps
> {
  constructor(props: RouteComponentProps<any>) {
    super(props);
    this.state = {
      isLoading: false,
    };
  }

  onFinish = (values: any) => {
    this.setState({ isLoading: true });
    axios
      .post(window.location.origin + '/core/multiverse/universities', values)
      .then((result: AxiosResponse<University>) => {
        if (result.status == 200) {
          notification.success({
            message: 'Success!',
            description: 'Successfully registered the university',
          });
          this.setState({ isLoading: false });
          this.props.history.push('/dashboard/universities/');
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Error occurred while trying to save the university'
        );
        this.setState({ isLoading: false });
      });
  };
  render() {
    return (
      <Row className={styles.innerContent}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Add a new University</Title>

          <Row>
            <Col md={12}>
              <Spin tip="Loading..." spinning={this.state.isLoading}>
                <Form
                  {...layout}
                  name="basic"
                  onFinish={this.onFinish}
                  size="large"
                >
                  <Title level={3}>University&apos;s name</Title>
                  <Text>Name of the university</Text>
                  <Form.Item
                    name="name"
                    rules={[
                      {
                        required: true,
                      },
                    ]}
                    className={styles.formItem}
                  >
                    <Input placeholder="ex: University of Colombo" />
                  </Form.Item>
                  <Title level={3}>Logo</Title>
                  <Text>Paste an image URL of the university&apos;s logo</Text>
                  <Form.Item
                    name="imageUrl"
                    className={styles.formItem}
                    rules={[{ required: true }]}
                  >
                    <Input placeholder="https://www.abc.lk/logo.jpg" />
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary" htmlType="submit">
                      Add University
                    </Button>
                  </Form.Item>
                </Form>
              </Spin>
            </Col>
          </Row>
        </Col>
      </Row>
    );
  }
}

export default withRouter(AddUniversity);

import React from 'react';
import {
  Button,
  Col,
  Form,
  Input,
  notification,
  Row,
  Spin,
  Typography,
} from 'antd';
import mainStyles from '../../styles.css';
import { AddCategoryStateProps } from './interfaces';
import { RouteComponentProps, withRouter } from 'react-router';
import axios, { AxiosResponse } from 'axios';
import { Category } from '../../interfaces';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;

class AddCategory extends React.Component<
  RouteComponentProps,
  AddCategoryStateProps
> {
  constructor(props: any) {
    super(props);
    this.state = {
      isLoading: false,
    };
  }

  onFinish = (values: any) => {
    this.setState({ isLoading: true });
    axios
      .post(window.location.origin + '/core/academix/admin/categories', values)
      .then((res: AxiosResponse<Category>) => {
        if (res.status == 201) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Successfully Created a Category',
          });
          this.props.history.push('/dashboard/academix/categories');
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(
          error,
          'Something went wrong when creating the category'
        );
      });
  };

  render() {
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Title>Add a new Category</Title>
            <Row>
              <Col md={12}>
                <Form size={'large'} onFinish={this.onFinish}>
                  <Spin spinning={this.state.isLoading}>
                    <Title level={3}>Name</Title>
                    <Text>Name of the Category</Text>
                    <Form.Item
                      name={'name'}
                      className={mainStyles.formItem}
                      rules={[
                        {
                          required: true,
                          message: 'Please enter a category name!',
                        },
                      ]}
                    >
                      <Input placeholder="ex: KG & Primary" />
                    </Form.Item>
                    <Form.Item>
                      <Button type="primary" htmlType="submit">
                        Add Category
                      </Button>
                    </Form.Item>
                  </Spin>
                </Form>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(AddCategory);

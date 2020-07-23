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
import { AddCategoryStateProps, CategoryUrlParams } from './interfaces';
import { RouteComponentProps, withRouter } from 'react-router';
import axios, { AxiosResponse, Method } from 'axios';
import { Category } from '../../interfaces';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;

class UpsertCategory extends React.Component<
  RouteComponentProps<CategoryUrlParams>,
  AddCategoryStateProps
> {
  categoryId: string;
  componentType: 'add' | 'edit';

  constructor(props: RouteComponentProps<CategoryUrlParams>) {
    super(props);
    this.categoryId = this.props.match.params.categoryId;
    // If the category id is not present in the url sets 'add' otherwise sets 'edit'
    this.componentType = this.categoryId ? 'edit' : 'add';
    this.state = {
      isLoading: false,
      category: null,
    };
  }

  componentDidMount() {
    if (this.componentType == 'edit') {
      this.fetchCategory();
    }
  }

  fetchCategory = () => {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin + '/core/academix/categories/' + this.categoryId
      )
      .then((result: AxiosResponse<Category>) => {
        if (result.status == 200) {
          this.setState({ isLoading: false, category: result.data });
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(error, 'Something went wrong trying to load category');
        this.setState({ isLoading: false });
      });
  };

  onFinish = (values: any) => {
    let statusCode: number, method: Method, url: string;
    this.setState({ isLoading: true });
    // Checks for the component type and sets the relevant statusCode, url and method to call
    if (this.componentType == 'edit') {
      statusCode = 200;
      method = 'put';
      url = `/core/academix/admin/categories/${this.categoryId}`;
    } else {
      statusCode = 201;
      method = 'post';
      url = '/core/academix/admin/categories';
    }
    axios({
      method: method,
      url: window.location.origin + url,
      data: {
        id: this.categoryId,
        name: values.name,
      },
    })
      .then((res: AxiosResponse<Category>) => {
        if (res.status == statusCode) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Category saved!',
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
            {this.componentType == 'edit' ? (
              <Title>Update Category</Title>
            ) : (
              <Title>Add a new Category</Title>
            )}
            <Spin tip="Loading..." spinning={this.state.isLoading}>
              {/* Renders the form straight away if the componentType is 'add',
            if it's 'edit' waits for the component to get the category
            and then renders the pre-data-filled-form*/}
              {(this.state.category != null || this.componentType == 'add') && (
                <Row>
                  <Col md={12}>
                    <Form
                      size={'large'}
                      initialValues={{ name: this.state.category?.name }}
                      onFinish={this.onFinish}
                    >
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
                          Save
                        </Button>
                      </Form.Item>
                    </Form>
                  </Col>
                </Row>
              )}
            </Spin>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(UpsertCategory);

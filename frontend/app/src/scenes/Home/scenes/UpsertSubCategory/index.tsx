import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
import { AddSubCategoryStateProps, SubCategoryUrlParams } from './interfaces';
import { Category, SubCategory } from '../../interfaces';
import {
  Button,
  Col,
  Form,
  Input,
  notification,
  Row,
  Select,
  Spin,
  Typography,
} from 'antd';
import mainStyles from '../../styles.css';
import axios, { AxiosResponse, Method } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;
const { Option } = Select;

class AddSubCategory extends React.Component<
  RouteComponentProps<SubCategoryUrlParams>,
  AddSubCategoryStateProps
> {
  subCategoryId: string | null;
  componentType: 'edit' | 'add';

  constructor(props: RouteComponentProps<SubCategoryUrlParams>) {
    super(props);
    this.subCategoryId = this.props.match.params.subCategoryId;
    this.componentType = 'add';
    // Set component type to "edit" if the subcategory id exist on the url
    if (this.subCategoryId != null) {
      this.componentType = 'edit';
    }
    this.state = {
      isLoading: false,
      categories: [],
      subcategory: null,
    };
  }

  componentDidMount() {
    this.fetchCategories();
    // Fetch the current subcategory details if the component type is "edit"
    if (this.componentType == 'edit') {
      this.fetchSubCategoryDetails();
    }
  }

  fetchCategories = () => {
    axios
      .get(window.location.origin + '/core/academix/categories')
      .then((result: AxiosResponse<Category[]>) => {
        if (result.status == 200) {
          this.setState({ categories: result.data, isLoading: false });
        } else {
          throw new Error();
        }
      })
      .catch((error) =>
        handleApiError(
          error,
          'Something went wrong when trying to load categories'
        )
      );
  };

  // Fetch the current SubCategory details to set initial values
  fetchSubCategoryDetails = () => {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          `/core/academix/sub-categories/${this.subCategoryId}`
      )
      .then((result: AxiosResponse<SubCategory>) => {
        if (result.status == 200) {
          this.setState({
            subcategory: result.data,
            isLoading: false,
          });
        } else {
          throw new Error();
        }
      })
      .catch((error) =>
        handleApiError(
          error,
          'Something went wrong when trying to load the subcategory'
        )
      );
  };

  onFinish = (values: any) => {
    // Checks component type to change the method, url and data
    let statusCode: number, method: Method, url;
    if (this.componentType == 'add') {
      statusCode = 201;
      method = 'post';
      url = '/core/academix/admin/sub-categories/';
    } else {
      statusCode = 200;
      method = 'put';
      url = `/core/academix/admin/sub-categories/${this.subCategoryId}`;
    }
    this.setState({ isLoading: true });
    axios({
      method: method,
      url: window.location.origin + url,
      data: {
        id: this.subCategoryId,
        name: values.name,
        category: {
          id: values.category,
        },
      },
    })
      .then((result: AxiosResponse<SubCategory>) => {
        if (result.status == statusCode) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'The subcategory saved successfully!',
          });
          this.props.history.push(
            `/dashboard/academix/${values.category}/${values.name}`
          );
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(
          error,
          'Something went wrong when creating the subcategory'
        );
      });
  };

  render() {
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Spin tip="Loading..." spinning={this.state.isLoading}>
              <Title>
                {this.componentType == 'edit'
                  ? 'Edit Subcategory'
                  : 'Add a new Subcategory'}
              </Title>
              <Row>
                <Col md={12}>
                  {/* If the component's type is "edit", the form should not get rendered until the
                  subcategory loads but if it is "add", it should render the form */}
                  {(this.componentType != 'edit' ||
                    this.state.subcategory != null) && (
                    <Form
                      size="large"
                      onFinish={this.onFinish}
                      initialValues={{
                        category: this.state.subcategory?.category.id,
                        name: this.state.subcategory?.name,
                      }}
                    >
                      <Title level={3}>Category</Title>
                      <Text>Select a category</Text>
                      <Form.Item
                        name="category"
                        className={mainStyles.formItem}
                      >
                        <Select
                          className={mainStyles.formSelect}
                          placeholder="Please select a category"
                        >
                          {this.state.categories.map((category: Category) => {
                            return (
                              <Option key={category.id} value={category.id}>
                                {category.name}
                              </Option>
                            );
                          })}
                        </Select>
                      </Form.Item>
                      <Title level={3}>Name</Title>
                      <Text>Name of the subcategory</Text>
                      <Form.Item name="name" className={mainStyles.formItem}>
                        <Input placeholder="ex: Local Curriculum" />
                      </Form.Item>
                      <Form.Item>
                        <Button type="primary" htmlType="submit">
                          Save
                        </Button>
                      </Form.Item>
                    </Form>
                  )}
                </Col>
              </Row>
            </Spin>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(AddSubCategory);

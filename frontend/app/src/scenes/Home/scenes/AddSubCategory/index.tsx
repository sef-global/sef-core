import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
import { AddSubCategoryStateProps } from './interfaces';
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
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;
const { Option } = Select;

class AddSubCategory extends React.Component<
  RouteComponentProps,
  AddSubCategoryStateProps
> {
  constructor(props: RouteComponentProps) {
    super(props);
    this.state = {
      isLoading: false,
      categories: [],
    };
  }

  componentDidMount() {
    this.fetchCategories();
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

  onFinish = (values: any) => {
    this.setState({ isLoading: true });
    const subCategory = {
      name: values.name,
      category: {
        id: values.category,
      },
    };
    axios
      .post(
        window.location.origin + '/core/academix/admin/sub-categories/',
        subCategory
      )
      .then((res: AxiosResponse<SubCategory>) => {
        if (res.status == 201) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Successfully Created a subcategory',
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
            <Title>Add a new Subcategory</Title>
            <Row>
              <Col md={12}>
                <Spin tip="Loading..." spinning={this.state.isLoading}>
                  <Form size="large" onFinish={this.onFinish}>
                    <Title level={3}>Category</Title>
                    <Text>Select a category</Text>
                    <Form.Item name="category" className={mainStyles.formItem}>
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
                        Add Subcategory
                      </Button>
                    </Form.Item>
                  </Form>
                </Spin>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(AddSubCategory);

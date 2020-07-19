import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
import { AddItemStateProps, ItemUrlParams } from './interfaces';
import { Item, SubCategory } from '../../interfaces';
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

class AddItem extends React.Component<
  RouteComponentProps<ItemUrlParams>,
  AddItemStateProps
> {
  itemId: string | null;
  componentType: 'edit' | 'add';

  constructor(props: RouteComponentProps<ItemUrlParams>) {
    super(props);
    this.itemId = this.props.match.params.itemId;
    this.componentType = 'add';
    // Set the component type to "edit" if the item id exist on the url
    if (this.itemId != null) {
      this.componentType = 'edit';
    }
    this.state = {
      isLoading: false,
      subCategories: [],
      item: null,
    };
  }

  componentDidMount() {
    this.fetchSubCategories();
    // Fetch the current item details if the component type is "edit"
    if (this.componentType == 'edit') {
      this.fetchItemDetails();
    }
  }

  fetchSubCategories = () => {
    this.setState({ isLoading: true });
    axios
      .get(window.location.origin + '/core/academix/sub-categories')
      .then((result: AxiosResponse<SubCategory[]>) => {
        if (result.status == 200) {
          this.setState({ subCategories: result.data, isLoading: false });
        } else {
          throw new Error();
        }
      })
      .catch((error) =>
        handleApiError(
          error,
          'Something went wrong when trying to load sub-categories'
        )
      );
  };
  // Fetch the current item details to set initial values
  fetchItemDetails = () => {
    this.setState({ isLoading: true });
    axios
      .get(window.location.origin + `/core/academix/items/${this.itemId}`)
      .then((result: AxiosResponse<Item>) => {
        if (result.status == 200) {
          this.setState({ item: result.data, isLoading: false });
        } else {
          throw new Error();
        }
      })
      .catch((error) =>
        handleApiError(
          error,
          'Something went wrong when trying to load the item'
        )
      );
  };

  onFinish = (values: any) => {
    // Checks component type to change the method, url and data
    let statusCode: number, method: Method, url;
    if (this.componentType == 'add') {
      statusCode = 201;
      method = 'post';
      url = '/core/academix/admin/items';
    } else {
      statusCode = 200;
      method = 'put';
      url = `/core/academix/admin/items/${this.itemId}`;
    }
    this.setState({ isLoading: true });
    const subCategoryIds: { id: number }[] = [];
    values.subCategories.map((subCategoryId: number) =>
      subCategoryIds.push({
        id: subCategoryId,
      })
    );
    axios({
      method: method,
      url: window.location.origin + url,
      data: {
        id: this.itemId,
        name: values.name,
        link: values.link,
        description: values.description,
        subCategories: subCategoryIds,
      },
    })
      .then((result: AxiosResponse<SubCategory>) => {
        if (result.status == statusCode) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'The item saved successfully!',
          });
          this.props.history.push('/dashboard/academix/categories');
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(error, 'Something went wrong when creating the item');
      });
  };

  render() {
    const subCategoryIds: number[] = [];
    this.state.item?.subCategories.map((subCategory: SubCategory) =>
      subCategoryIds.push(subCategory.id)
    );
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Spin tip="Loading..." spinning={this.state.isLoading}>
              <Title>
                {this.componentType == 'edit' ? 'Edit Item' : 'Add a new Item'}
              </Title>
              <Row>
                <Col md={12}>
                  {/* If the component's type is "edit", the form should not get rendered until the item
                  details loads but if it is "add" it should render the form */}
                  {(this.componentType != 'edit' ||
                    this.state.item != null) && (
                    <Form
                      size="large"
                      onFinish={this.onFinish}
                      initialValues={{
                        link: this.state.item?.link,
                        name: this.state.item?.name,
                        description: this.state.item?.description,
                        subCategories: subCategoryIds,
                      }}
                    >
                      <Title level={3}>Sub-Categories</Title>
                      <Text>Select Sub Categories for the item</Text>
                      <Form.Item
                        name="subCategories"
                        className={mainStyles.formItem}
                      >
                        <Select
                          mode="multiple"
                          className={mainStyles.formSelect}
                          placeholder="Please select"
                        >
                          {this.state.subCategories.map(
                            (subCategory: SubCategory) => {
                              return (
                                <Option
                                  key={subCategory.id}
                                  value={subCategory.id}
                                >
                                  {subCategory.category.name} :{' '}
                                  {subCategory.name}
                                </Option>
                              );
                            }
                          )}
                        </Select>
                      </Form.Item>
                      <Title level={3}>Link</Title>
                      <Text>Link of the source of the Item</Text>
                      <Form.Item name="link" className={mainStyles.formItem}>
                        <Input placeholder="ex: https://www.abc.com" />
                      </Form.Item>
                      <Title level={3}>Name</Title>
                      <Text>Name of the Item</Text>
                      <Form.Item name="name" className={mainStyles.formItem}>
                        <Input placeholder="ex: Reasons to do a PhD" />
                      </Form.Item>
                      <Title level={3}>Description</Title>
                      <Text>Description of the item</Text>
                      <Form.Item
                        name="description"
                        className={mainStyles.formItem}
                      >
                        <Input.TextArea />
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

export default withRouter(AddItem);

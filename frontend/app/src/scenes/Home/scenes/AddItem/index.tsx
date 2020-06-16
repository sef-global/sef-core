import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
import { AddItemStateProps, AddItemPayload } from './interfaces';
import { SubCategory } from '../../interfaces';
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

class AddItem extends React.Component<
  RouteComponentProps<any>,
  AddItemStateProps
> {
  constructor(props: RouteComponentProps<any>) {
    super(props);
    this.state = {
      isLoading: false,
      subCategories: [],
    };
  }

  componentDidMount() {
    this.fetchSubCategories();
  }

  fetchSubCategories = () => {
    axios
      .get(
        window.location.origin + '/core/academix/categories/1/sub-categories'
      )
      .then((result: AxiosResponse<SubCategory[]>) => {
        if (result.status == 200) {
          this.setState({ subCategories: result.data, isLoading: false });
        }
      })
      .catch((error) =>
        handleApiError(
          error,
          'Something went wrong when trying to load sub-categories'
        )
      );
  };

  onFinish = (values: any) => {
    this.setState({ isLoading: true });
    const item = {
      link: values.link,
      translations: [
        {
          name: values.name,
          description: values.description,
          language: 'en',
        },
      ],
    };
    console.log(item);
    console.log(values.subCategories);
    axios
      .post(
        `${window.location.origin}/core/academix/admin/item?subCategoryId=${values.subCategories}`,
        item
      )
      .then((res: AxiosResponse<AddItemPayload>) => {
        if (res.status == 201) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Successfully Created an Item',
          });
          this.props.history.push(
            `${window.location.origin}/dashboard/item/${res.data.id}/edit`
          );
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(error, 'Something went wrong when creating the item');
      });
  };

  render() {
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Title>Add a new Item</Title>
            <Row>
              <Col md={12}>
                <Spin tip="Loading..." spinning={this.state.isLoading}>
                  <Form size="large" onFinish={this.onFinish}>
                    <Title level={3}>Sub-Categories</Title>
                    <Text>Select Sub Categories for the item</Text>
                    <Form.Item
                      name="subCategories"
                      className={mainStyles.formItem}
                    >
                      <Select
                        mode="multiple"
                        style={{ width: '100%' }}
                        placeholder="Please select"
                      >
                        {this.state.subCategories.map(
                          (subCategory: SubCategory) => {
                            return (
                              <Option
                                key={subCategory.id}
                                value={subCategory.id}
                              >
                                {subCategory.translations[0].name}
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
                        Add Item
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

export default withRouter(AddItem);

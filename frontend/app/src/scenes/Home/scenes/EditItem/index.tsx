import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
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
import { Item, SubCategory } from '../../interfaces';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';
import { AddItemPayload } from '../AddItem/interfaces';
import { EditItemStateProps } from './interfaces';

const { Title, Text } = Typography;
const { Option } = Select;

class EditItem extends React.Component<
  RouteComponentProps<any>,
  EditItemStateProps
> {
  constructor(props: RouteComponentProps) {
    super(props);
    this.state = {
      isLoading: false,
      subCategories: [],
      item: null,
    };
  }

  componentDidMount() {
    this.fetchItem();
    this.fetchSubCategories();
  }

  fetchSubCategories = () => {
    this.setState({ isLoading: true });
    axios
      .get(window.location.origin + '/core/academix/sub-categories')
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

  fetchItem = () => {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          '/core/academix/items/' +
          this.props.match.params.id
      )
      .then((result: AxiosResponse<Item>) => {
        if (result.status == 200) {
          this.setState({ item: result.data, isLoading: false });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load the Item'
        );
        this.props.history.goBack();
      });
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
    axios
      .put(
        `${window.location.origin}/core/academix/admin/items/${this.state.item?.id}`,
        item
      )
      .then((res: AxiosResponse<AddItemPayload>) => {
        if (res.status == 201) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Successfully Updated the Item',
          });
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(error, 'Something went wrong when updating the item');
      });
  };

  render() {
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Spin tip="Loading..." spinning={this.state.isLoading}>
              <Title>Update item: {this.state.item?.id}</Title>
              <Row>
                <Col md={12}>
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
                      <Input
                        value={this.state.item?.link}
                        placeholder="ex: https://www.abc.com"
                      />
                    </Form.Item>
                    <Title level={3}>Name</Title>
                    <Text>Name of the Item</Text>
                    <Form.Item name="name" className={mainStyles.formItem}>
                      <Input
                        value={this.state.item?.translations[0].name}
                        placeholder="ex: Reasons to do a PhD"
                      />
                    </Form.Item>
                    <Title level={3}>Description</Title>
                    <Text>Description of the item</Text>
                    <Form.Item
                      name="description"
                      className={mainStyles.formItem}
                    >
                      <Input.TextArea
                        value={this.state.item?.translations[0].description}
                      />
                    </Form.Item>
                    <Form.Item>
                      <Button type="primary" htmlType="submit">
                        Add Item
                      </Button>
                    </Form.Item>
                  </Form>
                </Col>
              </Row>
            </Spin>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(EditItem);

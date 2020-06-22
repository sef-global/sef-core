import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router';
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
import { Item } from '../../interfaces';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';
import { AddItemPayload } from '../AddItem/interfaces';
import { EditItemStateProps, ItemUrlParams } from './interfaces';

const { Title, Text } = Typography;

class EditItem extends React.Component<
  RouteComponentProps<ItemUrlParams>,
  EditItemStateProps
> {
  constructor(props: RouteComponentProps<ItemUrlParams>) {
    super(props);
    this.state = {
      isLoading: false,
      item: null,
    };
  }

  componentDidMount() {
    this.fetchItem();
  }

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
        `${window.location.origin}/core/academix/admin/items/` +
          `${this.state.item?.id}/translations?isAllReset=false`,
        item
      )
      .then((res: AxiosResponse<AddItemPayload>) => {
        if (res.status == 202) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Successfully Updated the Item',
          });
          this.fetchItem();
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
              <Title level={2}>Update item: {this.state.item?.id}</Title>
              <Row gutter={26}>
                <Col md={12}>
                  {this.state.item != null && (
                    <Form
                      size="large"
                      onFinish={this.onFinish}
                      initialValues={{
                        link: this.state.item?.link,
                        name: this.state.item?.translations[0].name,
                        description: this.state.item?.translations[0]
                          .description,
                      }}
                    >
                      <Title level={3}>Link</Title>
                      <Text>Link of the source of the Item</Text>
                      <Form.Item name="link" className={mainStyles.formItem}>
                        <Input />
                      </Form.Item>
                      <Title level={3}>Name</Title>
                      <Text>Name of the Item</Text>
                      <Form.Item name="name" className={mainStyles.formItem}>
                        <Input />
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
                          Update
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

export default withRouter(EditItem);

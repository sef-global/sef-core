import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { ItemStateProps, ItemPayload, ItemUrlParams } from './interfaces';
import { Button, Col, Divider, Popconfirm, Row, Table, Typography } from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../../../../services/util/errorHandler';
const { Title } = Typography;
import { RouteComponentProps } from 'react-router';

class Items extends React.Component<
  RouteComponentProps<ItemUrlParams>,
  ItemStateProps
> {
  subCategoryId: string;
  pageSize: number;
  constructor(props: RouteComponentProps<ItemUrlParams>) {
    super(props);
    this.subCategoryId = this.props.match.params.subCategoryId;
    this.pageSize = 8;
    this.state = {
      isLoading: false,
      items: [],
      pagination: {
        current: 1,
        total: 0,
        pageSize: this.pageSize,
      },
    };
  }

  componentDidMount() {
    this.fetchItems();
  }

  fetchItems = (pageNo = 1) => {
    const pageNumber = pageNo - 1;
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          '/core/academix/sub-categories/' +
          `${this.subCategoryId}/items?pageNumber=${pageNumber}&pageSize=${this.pageSize}`
      )
      .then((result: AxiosResponse<ItemPayload>) => {
        if (result.status == 200) {
          const pagination = { ...this.state.pagination };
          pagination.current = pageNo;
          pagination.total = result.data.totalElements;
          this.setState({
            isLoading: false,
            items: result.data.content,
            pagination: pagination,
          });
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(error, 'Something went wrong when trying to load items');
        this.setState({ isLoading: false });
      });
  };

  handleDelete = (id: number) => {
    this.setState({ isLoading: true });
    axios
      .delete(window.location.origin + `/core/academix/admin/items/${id}`)
      .then((result) => {
        if (result.status == 204) {
          this.setState({ isLoading: false });
          this.fetchItems();
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to delete the item'
        );
        this.setState({ isLoading: false });
      });
  };

  columns = [
    {
      title: 'Id',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      key: 'edit',
      dataIndex: 'id',
      // eslint-disable-next-line react/display-name
      render: (id: number) => (
        <div>
          <Button type="link">Edit</Button>
          <Divider type="vertical" />
          <Popconfirm
            title="Sure to delete"
            onConfirm={() => this.handleDelete(id)}
          >
            <Button type="link" danger>
              Delete
            </Button>
          </Popconfirm>
        </div>
      ),
    },
  ];

  render() {
    return (
      <Row className={styles.content}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Items</Title>
          <Table
            rowKey="id"
            columns={this.columns}
            dataSource={this.state.items}
            loading={this.state.isLoading}
            className={styles.column}
            pagination={{
              onChange: this.fetchItems,
              pageSize: this.pageSize,
              total: this.state.pagination.total,
            }}
          />
        </Col>
      </Row>
    );
  }
}

export default Items;

import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { ItemStateProps, ItemPayload, ItemUrlParams } from './interfaces';
import { Col, Row, Table, Typography } from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../../../../services/util/errorHandler';
const { Title } = Typography;
import { RouteComponentProps } from 'react-router';

const columns = [
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
    // eslint-disable-next-line react/display-name
    render: () => <a>Edit</a>,
  },
];

class Items extends React.Component<
  RouteComponentProps<ItemUrlParams>,
  ItemStateProps
> {
  SubCategoryId: string;
  pageSize: number;
  constructor(props: RouteComponentProps<ItemUrlParams>) {
    super(props);
    this.SubCategoryId = this.props.match.params.subCategoryId;
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
          `${this.SubCategoryId}/items?pageNumber=${pageNumber}&pageSize=${this.pageSize}`
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

  render() {
    return (
      <Row className={styles.content}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Items</Title>
          <Table
            rowKey="id"
            columns={columns}
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

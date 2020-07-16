import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { CategoryStateProps } from './interfaces';
import { Category } from '../../interfaces';
import {
  Button,
  Col,
  Divider,
  Popconfirm,
  Row,
  Space,
  Table,
  Typography,
} from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../services/util/errorHandler';
import { Link } from 'react-router-dom';
const { Title } = Typography;

class Categories extends React.Component<{}, CategoryStateProps> {
  constructor(props: {}) {
    super(props);
    this.state = {
      isLoading: false,
      categories: [],
    };
  }
  componentDidMount() {
    this.fetchCategories();
  }

  fetchCategories() {
    this.setState({ isLoading: true });
    axios
      .get(window.location.origin + '/core/academix/categories')
      .then((result: AxiosResponse<Category[]>) => {
        if (result.status == 200) {
          this.setState({
            isLoading: false,
            categories: result.data,
          });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load categories'
        );
        this.setState({ isLoading: false });
      });
  }

  handleDelete = (id: number) => {
    this.setState({ isLoading: true });
    axios
      .delete(window.location.origin + `/core/academix/admin/categories/${id}`)
      .then((result) => {
        if (result.status == 204) {
          this.setState({ isLoading: false });
          this.fetchCategories();
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to delete the category'
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
      // eslint-disable-next-line react/display-name
      render: (name: string, record: Category) => {
        const categoryName = name
          .trim()
          .replace(/\s+|\//g, '-')
          .toLowerCase();
        return <Link to={`${record.id}/${categoryName}`}>{name}</Link>;
      },
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
          <Space className={styles.space}>
            <Title>Categories</Title>
            <Link
              className={styles.button}
              to={'/dashboard/academix/category/add'}
            >
              <Button type="primary" shape="round">
                Add New Category
              </Button>
            </Link>
          </Space>
          <Table
            rowKey="id"
            columns={this.columns}
            dataSource={this.state.categories}
            loading={this.state.isLoading}
            className={styles.column}
          />
        </Col>
      </Row>
    );
  }
}

export default Categories;

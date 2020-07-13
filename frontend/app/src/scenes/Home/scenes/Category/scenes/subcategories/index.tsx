import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { CategoryStateProps, CategoryUrlParams } from './interfaces';
import { SubCategory } from '../../../../interfaces';
import { Button, Col, Divider, Popconfirm, Row, Table, Typography } from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../../../services/util/errorHandler';
import { Link } from 'react-router-dom';
const { Title } = Typography;
import { RouteComponentProps } from 'react-router';

class SubCategories extends React.Component<
  RouteComponentProps<CategoryUrlParams>,
  CategoryStateProps
> {
  categoryId: string;
  constructor(props: RouteComponentProps<CategoryUrlParams>) {
    super(props);
    this.categoryId = this.props.match.params.categoryId;
    this.state = {
      isLoading: false,
      subCategories: [],
    };
  }
  componentDidMount() {
    this.fetchSubCategories();
  }

  fetchSubCategories() {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          `/core/academix/categories/${this.categoryId}/sub-categories`
      )
      .then((result: AxiosResponse<SubCategory[]>) => {
        if (result.status == 200) {
          this.setState({
            isLoading: false,
            subCategories: result.data,
          });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load sub-categories'
        );
        this.setState({ isLoading: false });
      });
  }

  handleDelete = (id: number) => {
    this.setState({ isLoading: true });
    axios
      .delete(
        window.location.origin + `/core/academix/admin/sub-categories/${id}`
      )
      .then((result) => {
        if (result.status == 204) {
          this.setState({ isLoading: false });
          this.fetchSubCategories();
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to delete the subcategory'
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
      render: (name: string, record: SubCategory) => {
        const subCategoryName = name
          .trim()
          .replace(/\s+|\//g, '-')
          .toLowerCase();
        return (
          <Link
            to={window.location.pathname + `/${record.id}/${subCategoryName}`}
          >
            {name}
          </Link>
        );
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
          <Title>Subcategories</Title>
          <Table
            rowKey="id"
            columns={this.columns}
            dataSource={this.state.subCategories}
            loading={this.state.isLoading}
            className={styles.column}
          />
        </Col>
      </Row>
    );
  }
}

export default SubCategories;

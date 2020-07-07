import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { CategoryStateProps, CategoryUrlParams } from './interfaces';
import { SubCategory } from '../../../../interfaces';
import { Col, Row, Table, Typography } from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../../../services/util/errorHandler';
import { Link } from 'react-router-dom';
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
    // eslint-disable-next-line react/display-name
    render: (text: string, record: SubCategory) => {
      const subCategoryName = text
        .trim()
        .replace(/\s+|\//g, '-')
        .toLowerCase();
      return (
        <Link
          to={window.location.pathname + `/${record.id}/${subCategoryName}`}
        >
          {text}
        </Link>
      );
    },
  },
  {
    key: 'edit',
    // eslint-disable-next-line react/display-name
    render: () => <a>Edit</a>,
  },
];

class SubCategories extends React.Component<
  RouteComponentProps<CategoryUrlParams>,
  CategoryStateProps
> {
  CategoryId: string;
  constructor(props: RouteComponentProps<CategoryUrlParams>) {
    super(props);
    this.CategoryId = this.props.match.params.categoryId;
    this.state = {
      isLoading: false,
      subCategories: [],
    };
  }

  componentDidMount() {
    axios
      .get(
        window.location.origin +
          `/core/academix/categories/${this.CategoryId}/sub-categories`
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
  render() {
    return (
      <Row className={styles.content}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Subcategories</Title>
          <Table
            rowKey="id"
            columns={columns}
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

import React from 'react';
import axios, { AxiosResponse } from 'axios';
import { CategoryStateProps } from './interfaces';
import { Translation, Category } from '../../interfaces';
import { Col, Row, Table, Typography } from 'antd';
import styles from './styles.css';
import { handleApiError } from '../../../../services/util/errorHandler';
const { Title } = Typography;

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: 'name',
    dataIndex: 'translations',
    key: 'name',
    // eslint-disable-next-line react/display-name
    render: (translations: Translation[]) => (
      <Row>
        return (
        <Col key={translations[0].name}>
          <p>
            {translations[0].name} : {translations[0].language}
          </p>
        </Col>
        );
      </Row>
    ),
  },
];

class Categories extends React.Component<{}, CategoryStateProps> {
  constructor(props: {}) {
    super(props);
    this.state = {
      isLoading: false,
      categories: [],
    };
  }

  componentDidMount() {
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

  render() {
    return (
      <Row className={styles.content}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Categories</Title>
          <Table
            rowKey="id"
            columns={columns}
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

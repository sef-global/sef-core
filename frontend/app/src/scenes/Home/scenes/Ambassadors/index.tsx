import React from 'react';
import { Typography, Table, Row, Col } from 'antd';
import styles from './styles.css';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';
import { University } from '../../interfaces';
import { PaginationProps } from 'antd/lib/pagination';
import { AmbassadorPayload, AmbassadorsStateProps } from './interfaces';
const { Title } = Typography;

const columns = [
  {
    title: 'First Name',
    dataIndex: 'firstName',
    key: 'firstName',
  },
  {
    title: 'Last Name',
    dataIndex: 'lastName',
    key: 'lastName',
  },
  {
    title: 'University',
    dataIndex: 'university',
    key: 'university',
    // eslint-disable-next-line react/display-name
    render: (university: University) => university.name,
  },
];

class Ambassadors extends React.Component<{}, AmbassadorsStateProps> {
  pageSize: number;
  constructor(props: {}) {
    super(props);
    this.pageSize = 10;
    this.state = {
      isLoading: false,
      ambassadors: [],
      pagination: {
        current: 1,
        total: 0,
        pageSize: this.pageSize,
      },
    };
  }

  componentDidMount() {
    this.fetchAmbassadors(1);
  }

  fetchAmbassadors = (pageNo: number) => {
    const pageNumber = pageNo - 1;
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          `/core/multiverse/ambassadors?pageNumber=${pageNumber}&limit=${this.pageSize}`
      )
      .then((result: AxiosResponse<AmbassadorPayload>) => {
        if (result.status == 200) {
          const pagination = { ...this.state.pagination };
          pagination.current = pageNo;
          pagination.total = result.data.totalElements;
          this.setState({
            isLoading: false,
            ambassadors: result.data.content,
            pagination: pagination,
          });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load ambassadors'
        );
        this.setState({ isLoading: false });
      });
  };

  handleTableChange = (pagination: PaginationProps) => {
    if (pagination.current != undefined) {
      this.fetchAmbassadors(pagination.current);
    }
  };

  render() {
    return (
      <Row className={styles.content}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Ambassadors</Title>
          <Table
            rowKey="id"
            columns={columns}
            dataSource={this.state.ambassadors}
            pagination={this.state.pagination}
            onChange={this.handleTableChange}
            loading={this.state.isLoading}
            className={styles.column}
          />
        </Col>
      </Row>
    );
  }
}

export default Ambassadors;

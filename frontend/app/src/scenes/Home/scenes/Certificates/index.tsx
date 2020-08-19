import React from 'react';
import axios, { AxiosResponse } from 'axios';
import styles from '../Category/styles.css';
import {
  Button,
  Col,
  Divider,
  notification,
  Popconfirm,
  Row,
  Space,
  Table,
  Tooltip,
  Typography,
} from 'antd';
import { DeleteOutlined, EditOutlined, LinkOutlined } from '@ant-design/icons';
import { CertificatesStateProps, CertificatesPayload } from './interfaces';
import { Link } from 'react-router-dom';
import { handleApiError } from '../../../../services/util/errorHandler';
import CopyToClipboard from 'react-copy-to-clipboard';
const { Title } = Typography;

class Certificates extends React.Component<{}, CertificatesStateProps> {
  pageSize: number;
  constructor(props: {}) {
    super(props);
    this.pageSize = 10;
    this.state = {
      certificates: [],
      isLoading: false,
      pagination: {
        current: 1,
        total: 0,
        pageSize: this.pageSize,
      },
    };
  }

  componentDidMount() {
    this.fetchCertificates();
  }

  fetchCertificates = (pageNo = 1) => {
    const pageNumber = pageNo - 1;
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          `/core/fellowship/admin/certificates?pageSize=${this.pageSize}&pageNumber=${pageNumber}`
      )
      .then((res: AxiosResponse<CertificatesPayload>) => {
        if (res.status == 200) {
          const pagination = { ...this.state.pagination };
          pagination.current = pageNo;
          pagination.total = res.data.totalElements;
          this.setState({
            isLoading: false,
            certificates: res.data.content,
            pagination: pagination,
          });
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load certificates'
        );
        this.setState({ isLoading: false });
      });
  };

  handleDelete = (id: number) => {
    this.setState({ isLoading: true });
    axios
      .delete(
        window.location.origin + '/core/fellowship/admin/certificates/' + id
      )
      .then((res) => {
        if (res.status == 204) {
          this.fetchCertificates();
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'something went wrong when deleting the certificate'
        );
      });
    this.setState({ isLoading: false });
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
      // width: '40%',
      dataIndex: 'id',
      // eslint-disable-next-line react/display-name
      render: (id: number) => (
        <div>
          <CopyToClipboard
            text={
              window.location.origin + '/fellowship/?id=' + btoa(id.toString())
            }
            onCopy={() =>
              notification.success({
                message: 'Link Copied!',
                description:
                  window.location.origin +
                  '/fellowship/?id=' +
                  btoa(id.toString()),
              })
            }
          >
            <Tooltip placement="topLeft" title="Copy public link">
              <Button type={'link'}>
                <LinkOutlined />
              </Button>
            </Tooltip>
          </CopyToClipboard>
          <Divider type="vertical" />
          <Link to={`/dashboard/fellowship/certificate/${id}/edit`}>
            <Tooltip placement="topLeft" title="Edit">
              <Button type="link">
                <EditOutlined />
              </Button>
            </Tooltip>
          </Link>
          <Divider type="vertical" />
          <Popconfirm
            title="Sure to delete"
            onConfirm={() => this.handleDelete(id)}
          >
            <Tooltip placement="topLeft" title="Delete">
              <Button type="link" danger>
                <DeleteOutlined />
              </Button>
            </Tooltip>
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
            <Title>Certificates</Title>
          </Space>
          <Table
            rowKey="id"
            columns={this.columns}
            dataSource={this.state.certificates}
            loading={this.state.isLoading}
            className={styles.column}
            pagination={{
              onChange: this.fetchCertificates,
              pageSize: this.pageSize,
              total: this.state.pagination.total,
            }}
          />
        </Col>
      </Row>
    );
  }
}

export default Certificates;

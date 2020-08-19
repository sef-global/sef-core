import React from 'react';
import axios, { AxiosResponse } from 'axios';
import styles from '../Category/styles.css';
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
import { CertificatesStateProps, CertificatesPayload } from './interfaces';
import { Link } from 'react-router-dom';
import { handleApiError } from '../../../../services/util/errorHandler';
const { Title } = Typography;

class Certificates extends React.Component<{}, CertificatesStateProps> {
  constructor(props: {}) {
    super(props);
    this.state = {
      certificates: [],
      isLoading: false,
    };
  }

  componentDidMount() {
    this.fetchCertificates();
  }

  fetchCertificates = () => {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          '/core/fellowship/admin/certificates?pageSize=10&pageNumber=0'
      )
      .then((res: AxiosResponse<CertificatesPayload>) => {
        if (res.status == 200) {
          this.setState({
            isLoading: false,
            certificates: res.data.content,
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
      // // eslint-disable-next-line react/display-name
      // render: (name: string, record: Category) => {
      //   const categoryName = name
      //     .trim()
      //     .replace(/\s+|\//g, '-')
      //     .toLowerCase();
      //   return <Link to={`${record.id}/${categoryName}`}>{name}</Link>;
      // },
    },
    // {
    //   key: 'edit',
    //   dataIndex: 'id',
    //   // eslint-disable-next-line react/display-name
    //   render: (id: number) => (
    //     <div>
    //       <Link to={`/dashboard/academix/category/${id}/edit`}>
    //         <Button type="link">Edit</Button>
    //       </Link>
    //       <Divider type="vertical" />
    //       <Popconfirm
    //         title="Sure to delete"
    //         // onConfirm={() => this.handleDelete(id)}
    //       >
    //         <Button type="link" danger>
    //           Delete
    //         </Button>
    //       </Popconfirm>
    //     </div>
    //   ),
    // },
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
          />
        </Col>
      </Row>
    );
  }
}

export default Certificates;

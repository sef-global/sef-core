import React from 'react';
import { Typography, Table, Row, Col } from 'antd';
import styles from '../../styles.css';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';
import { University } from '../Universities';
const { Title } = Typography;

export interface Ambassador {
  id: number;
  firstName: string;
  lastName: string;
  university: string;
}

interface AmbassadorsStateProps {
  ambassadors: Ambassador[];
  isLoading: boolean;
}

const columns = [
  {
    title: 'First Name',
    dataIndex: 'firstName',
    key: 'firstName',
    // eslint-disable-next-line react/display-name
    render: (firstName: string) => <Title level={4}>{firstName}</Title>,
  },
  {
    title: 'Last Name',
    dataIndex: 'lastName',
    key: 'lastName',
    // eslint-disable-next-line react/display-name
    render: (lastName: string) => <Title level={4}>{lastName}</Title>,
  },
  {
    title: 'University',
    dataIndex: 'university',
    key: 'university',
    // eslint-disable-next-line react/display-name
    render: (university: University) => (
      <Title level={4}>{university.name}</Title>
    ),
  },
];

class Ambassadors extends React.Component<{}, AmbassadorsStateProps> {
  constructor(props: {}) {
    super(props);
    this.state = {
      isLoading: false,
      ambassadors: [],
    };
  }

  componentDidMount(): void {
    axios
      .get(window.location.origin + '/core/multiverse/ambassadors')
      .then((result: AxiosResponse<Ambassador[]>) => {
        if (result.status == 200) {
          this.setState({ isLoading: false, ambassadors: result.data });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load ambassadors'
        );
        this.setState({ isLoading: false });
      });
  }

  render() {
    return (
      <Row className={styles.innerContent}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Ambassadors</Title>
          <Table
            rowKey="id"
            columns={columns}
            dataSource={this.state.ambassadors}
          />
        </Col>
      </Row>
    );
  }
}

export default Ambassadors;

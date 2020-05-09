import React from 'react';
import { Typography, Table, Row, Col, Avatar } from 'antd';
import styles from '../../styles.css';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../services/util/errorHandler';
const { Title } = Typography;

export interface University {
  id: number;
  name: string;
  imageUrl: string;
}

interface UniversitiesStateProps {
  universities: University[];
  isLoading: boolean;
}

const columns = [
  {
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    width: 40,
    // eslint-disable-next-line react/display-name
    render: (imageUrl: string) => <Avatar size="large" src={imageUrl} />,
  },
  {
    dataIndex: 'name',
    key: 'name',
    // eslint-disable-next-line react/display-name
    render: (name: string) => <Title level={4}>{name}</Title>,
  },
];

class Universities extends React.Component<{}, UniversitiesStateProps> {
  constructor(props: {}) {
    super(props);
    this.state = {
      isLoading: false,
      universities: [],
    };
  }

  componentDidMount(): void {
    axios
      .get(window.location.origin + '/core/multiverse/universities')
      .then((result: AxiosResponse<University[]>) => {
        if (result.status == 200) {
          this.setState({ isLoading: false, universities: result.data });
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong when trying to load universities'
        );
        this.setState({ isLoading: false });
      });
  }

  render() {
    return (
      <Row className={styles.innerContent}>
        <Col md={24} lg={{ span: 20, offset: 2 }}>
          <Title>Universities</Title>
          <Table
            rowKey="id"
            columns={columns}
            dataSource={this.state.universities}
          />
        </Col>
      </Row>
    );
  }
}

export default Universities;

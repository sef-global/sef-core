import React from 'react';
import { Modal, Table, Spin, Button, Avatar, Typography } from 'antd';
import { University } from '../../../../../../interfaces';
import axios, { AxiosResponse } from 'axios';
import { handleApiError } from '../../../../../../../../services/util/errorHandler';
import {
  SearchUniversityProps,
  SearchUniversityStateProps,
} from './interfaces';

const { Title } = Typography;

class SearchUniversity extends React.Component<
  SearchUniversityProps,
  SearchUniversityStateProps
> {
  constructor(props: SearchUniversityProps) {
    super(props);
    this.state = {
      universities: [],
      isLoading: false,
    };
  }

  componentDidMount(): void {
    this.searchUniversities();
  }

  searchUniversities = () => {
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
  };

  columns = [
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
    {
      dataIndex: 'id',
      render: (id: number, university: University) => {
        return (
          <Button
            onClick={() => {
              this.props.onSelect(university);
            }}
          >
            Select
          </Button>
        );
      },
    },
  ];

  render() {
    return (
      <Modal
        title="Select a University"
        visible={this.props.isVisible}
        onCancel={this.props.onCancel}
        width="80%"
        footer={null}
      >
        <Spin spinning={this.state.isLoading}>
          <Table
            rowKey="id"
            columns={this.columns}
            dataSource={this.state.universities}
          />
        </Spin>
      </Modal>
    );
  }
}

export default SearchUniversity;

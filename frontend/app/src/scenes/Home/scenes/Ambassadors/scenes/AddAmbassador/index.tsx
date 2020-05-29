import React from 'react';
import {
  Col,
  Row,
  Typography,
  Form,
  Input,
  Button,
  Card,
  notification,
  Spin,
  Avatar,
} from 'antd';
import mainStyles from '../../../../styles.css';
import styles from './styles.css';
import axios, { AxiosResponse } from 'axios';
import { RouteComponentProps, withRouter } from 'react-router';
import { handleApiError } from '../../../../../../services/util/errorHandler';
import { University } from '../../../../interfaces';
import SearchUniversity from './components/SearchUniversity';
import { AddAmbassadorPayload, AddAmbassadorStateProps } from './interfaces';

const { Title, Text } = Typography;
const { Meta } = Card;
const layout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 },
};

class AddAmbassador extends React.Component<
  RouteComponentProps<any>,
  AddAmbassadorStateProps
> {
  constructor(props: RouteComponentProps<any>) {
    super(props);
    this.state = {
      isLoading: false,
      university: null,
      isSearchUniversityModalVisible: false,
    };
  }

  onFinish = (values: any) => {
    this.setState({ isLoading: true });
    axios
      .post(
        `${window.location.origin}/core/multiverse/universities/${this.state.university?.id}/ambassadors`,
        values
      )
      .then((result: AxiosResponse<AddAmbassadorPayload>) => {
        if (result.status == 200) {
          notification.success({
            message: 'Success!',
            description: 'Successfully registered the ambassador',
          });
          this.setState({ isLoading: false });
          this.props.history.push(
            '/dashboard/university/' + this.state.university?.id
          );
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Error occurred while trying to save the ambassador'
        );
        this.setState({ isLoading: false });
      });
  };

  changeUniversity = (university: University) => {
    this.setState({ university, isSearchUniversityModalVisible: false });
  };

  hideSearchUniversityModal = () => {
    this.setState({ isSearchUniversityModalVisible: false });
  };

  viewSearchUniversityModal = () => {
    this.setState({ isSearchUniversityModalVisible: true });
  };

  render() {
    return (
      <div>
        <SearchUniversity
          isVisible={this.state.isSearchUniversityModalVisible}
          onSelect={this.changeUniversity}
          onCancel={this.hideSearchUniversityModal}
        />
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            <Title>Add a new Ambassador</Title>
            <Row>
              <Col md={12}>
                <Spin tip="Loading..." spinning={this.state.isLoading}>
                  <Title level={3}>University</Title>
                  <Text>Select the university of the ambassador</Text>
                  <div className={styles.selectUniversity}>
                    {this.state.university == null ? (
                      <Button
                        type="primary"
                        htmlType="button"
                        onClick={this.viewSearchUniversityModal}
                      >
                        Select University
                      </Button>
                    ) : (
                      <Card
                        actions={[
                          <span
                            onClick={this.viewSearchUniversityModal}
                            key="change"
                          >
                            Change
                          </span>,
                        ]}
                      >
                        <Meta
                          avatar={
                            <Avatar
                              size="large"
                              style={{ backgroundColor: '#87d068' }}
                              src={this.state.university?.imageUrl}
                            />
                          }
                          title={`${this.state.university?.name}`}
                        />
                      </Card>
                    )}
                  </div>
                  <Form {...layout} onFinish={this.onFinish} size="large">
                    <Title level={3}>First Name</Title>
                    <Text>The first name of the ambassador</Text>
                    <Form.Item
                      name="firstName"
                      rules={[
                        {
                          required: true,
                        },
                      ]}
                      className={mainStyles.formItem}
                    >
                      <Input placeholder="ex: John" />
                    </Form.Item>
                    <Title level={3}>Last Name</Title>
                    <Text>The last name of the ambassador</Text>
                    <Form.Item
                      name="lastName"
                      rules={[
                        {
                          required: true,
                        },
                      ]}
                      className={mainStyles.formItem}
                    >
                      <Input placeholder="ex: Doe." />
                    </Form.Item>
                    <Form.Item>
                      <Button type="primary" htmlType="submit">
                        Add Ambassador
                      </Button>
                    </Form.Item>
                  </Form>
                </Spin>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(AddAmbassador);

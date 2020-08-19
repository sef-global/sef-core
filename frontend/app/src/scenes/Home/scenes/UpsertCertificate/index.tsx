import React from 'react';
import {
  Button,
  Col,
  Form,
  Input,
  notification,
  Row,
  Spin,
  Typography,
} from 'antd';
import mainStyles from '../../styles.css';
import {
  UpsertCertificateStateProps,
  CertificateUrlParams,
} from './interfaces';
import { RouteComponentProps, withRouter } from 'react-router';
import axios, { AxiosResponse, Method } from 'axios';
import { Certificate } from '../../interfaces';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;

class UpsertCertificate extends React.Component<
  RouteComponentProps<CertificateUrlParams>,
  UpsertCertificateStateProps
> {
  certificateId: string;
  componentType: 'add' | 'edit';

  constructor(props: RouteComponentProps<CertificateUrlParams>) {
    super(props);
    this.certificateId = this.props.match.params.id;
    // If the certificate id is not present in the url sets 'add' otherwise sets 'edit'
    this.componentType = this.certificateId ? 'edit' : 'add';
    this.state = {
      isLoading: false,
      certificate: null,
    };
  }

  componentDidMount() {
    if (this.componentType == 'edit') {
      this.fetchCategory();
    }
  }

  fetchCategory = () => {
    this.setState({ isLoading: true });
    axios
      .get(
        window.location.origin +
          '/core/fellowship/certificates/' +
          this.certificateId
      )
      .then((result: AxiosResponse<Certificate>) => {
        if (result.status == 200) {
          this.setState({ isLoading: false, certificate: result.data });
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(
          error,
          'Something went wrong trying to load certificate'
        );
        this.setState({ isLoading: false });
      });
  };

  onFinish = (values: any) => {
    let statusCode: number, method: Method, url: string;
    this.setState({ isLoading: true });
    // Checks for the component type and sets the relevant statusCode, url and method to call
    if (this.componentType == 'edit') {
      statusCode = 200;
      method = 'put';
      url = `/core/fellowship/admin/certificates/${this.certificateId}`;
    } else {
      statusCode = 201;
      method = 'post';
      url = '/core/fellowship/admin/certificates';
    }
    axios({
      method: method,
      url: window.location.origin + url,
      data: {
        id: this.certificateId,
        name: values.name,
      },
    })
      .then((res: AxiosResponse<Certificate>) => {
        if (res.status == statusCode) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Certificate saved!',
          });
          this.props.history.push('/dashboard/fellowship/certificates');
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(
          error,
          'Something went wrong when saving the certificate'
        );
      });
  };

  render() {
    return (
      <div>
        <Row className={mainStyles.innerContent}>
          <Col lg={24} xl={{ span: 20, offset: 2 }}>
            {this.componentType == 'edit' ? (
              <Title>Update Category</Title>
            ) : (
              <Title>Add a new Category</Title>
            )}
            <Spin tip="Loading..." spinning={this.state.isLoading}>
              {/* Renders the form straight away if the componentType is 'add',
            if it's 'edit' waits for the component to get the certificate
            and then renders the pre-data-filled-form*/}
              {(this.state.certificate != null ||
                this.componentType == 'add') && (
                <Row>
                  <Col md={12}>
                    <Form
                      size={'large'}
                      initialValues={{ name: this.state.certificate?.name }}
                      onFinish={this.onFinish}
                    >
                      <Title level={3}>Name</Title>
                      <Text>Name of the Receiver</Text>
                      <Form.Item
                        name={'name'}
                        className={mainStyles.formItem}
                        rules={[
                          {
                            required: true,
                            message: 'Please enter name!',
                          },
                        ]}
                      >
                        <Input placeholder="ex: Jon Doe" />
                      </Form.Item>
                      <Form.Item>
                        <Button type="primary" htmlType="submit">
                          Save
                        </Button>
                      </Form.Item>
                    </Form>
                  </Col>
                </Row>
              )}
            </Spin>
          </Col>
        </Row>
      </div>
    );
  }
}

export default withRouter(UpsertCertificate);

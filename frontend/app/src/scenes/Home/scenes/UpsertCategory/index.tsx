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
  Card,
  Select,
} from 'antd';
import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import mainStyles from '../../styles.css';
import { AddCategoryStateProps, CategoryUrlParams } from './interfaces';
import { RouteComponentProps, withRouter } from 'react-router';
import axios, { AxiosResponse, Method } from 'axios';
import { Category, Translation } from '../../interfaces';
import translationMetaData from './metaData';
import { handleApiError } from '../../../../services/util/errorHandler';

const { Title, Text } = Typography;
const { Option } = Select;

class UpsertCategory extends React.Component<
  RouteComponentProps<CategoryUrlParams>,
  AddCategoryStateProps
> {
  categoryId: string;
  componentType: 'add' | 'edit';

  constructor(props: RouteComponentProps<CategoryUrlParams>) {
    super(props);
    this.categoryId = this.props.match.params.categoryId;
    // If the category id is not present in the url sets 'add' otherwise sets 'edit'
    this.componentType = this.categoryId ? 'edit' : 'add';
    this.state = {
      isLoading: false,
      category: null,
      isInLanguageSelectionMode: false,
      translations: new Map(),
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
        window.location.origin + '/core/academix/categories/' + this.categoryId
      )
      .then((result: AxiosResponse<Category>) => {
        if (result.status == 200) {
          // Gets the translations for the fetched category and setting them to the state using a map
          const categoryTranslations = new Map();
          result.data.translations.forEach((translation) => {
            categoryTranslations.set(translation.language, {
              name: translation.name,
            });
          });
          this.setState({
            translations: categoryTranslations,
            isLoading: false,
            category: result.data,
          });
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        handleApiError(error, 'Something went wrong trying to load category');
        this.setState({ isLoading: false });
      });
  };

  onFinish = (values: any) => {
    let statusCode: number, method: Method, url: string;
    const translations: Translation[] = [];
    // Creating the translation array to send it with the payload
    Array.from(this.state.translations.entries()).map((translationEntry) => {
      const translation = {
        language: translationEntry[0],
        name: translationEntry[1].name,
      };
      translations.push(translation);
    });
    this.setState({ isLoading: true });
    // Checks for the component type and sets the relevant statusCode, url and method to call
    if (this.componentType == 'edit') {
      statusCode = 200;
      method = 'put';
      url = `/core/academix/admin/categories/${this.categoryId}`;
    } else {
      statusCode = 201;
      method = 'post';
      url = '/core/academix/admin/categories';
    }
    axios({
      method: method,
      url: window.location.origin + url,
      data: {
        id: this.categoryId,
        name: values.name,
        translations: translations,
      },
    })
      .then((res: AxiosResponse<Category>) => {
        if (res.status == statusCode) {
          this.setState({ isLoading: false });
          notification.success({
            message: 'Success!',
            description: 'Category saved!',
          });
          this.props.history.push('/dashboard/academix/categories');
        } else {
          throw new Error();
        }
      })
      .catch((error) => {
        this.setState({ isLoading: false });
        handleApiError(
          error,
          'Something went wrong when creating the category'
        );
      });
  };

  switchToLanguageSelectionMode = () => {
    this.setState({
      isInLanguageSelectionMode: true,
    });
  };

  // Checks for the selected language to render the relevant language card
  handleLanguageSelect = (language: string) => {
    const clonedTranslations = new Map(this.state.translations);
    clonedTranslations.set(language, { name: '' });
    this.setState({
      isInLanguageSelectionMode: false,
      translations: clonedTranslations,
    });
  };

  handleTranslationChange = (languageEnum: string, value: string) => {
    const clonedTranslations = new Map(this.state.translations);
    clonedTranslations.set(languageEnum, {
      name: value,
    });
    this.setState({
      translations: clonedTranslations,
    });
  };

  handleRemoveTranslation = (language: string) => {
    const clonedTranslations = new Map(this.state.translations);
    clonedTranslations.delete(language);
    this.setState({ translations: clonedTranslations });
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
            if it's 'edit' waits for the component to get the category
            and then renders the pre-data-filled-form*/}
              {(this.state.category != null || this.componentType == 'add') && (
                <Row>
                  <Col md={12}>
                    <Form
                      size={'large'}
                      initialValues={{
                        name: this.state.category?.name,
                      }}
                      onFinish={this.onFinish}
                    >
                      <Title level={3}>Name</Title>
                      <Text>Name of the Category</Text>
                      <Form.Item
                        name={'name'}
                        className={mainStyles.formItem}
                        rules={[
                          {
                            required: true,
                            message: 'Please enter a category name!',
                          },
                        ]}
                      >
                        <Input placeholder="ex: KG & Primary" />
                      </Form.Item>
                      <Title level={3}>Translations</Title>
                      {Array.from(this.state.translations.entries()).map(
                        (translationEntry) => {
                          const translationMeta = translationMetaData.get(
                            translationEntry[0]
                          );
                          if (translationMeta) {
                            return (
                              <Card
                                key={translationEntry[0]}
                                className={mainStyles.cardMargin}
                              >
                                <Button
                                  danger
                                  type="link"
                                  className={mainStyles.closeButton}
                                  onClick={() =>
                                    this.handleRemoveTranslation(
                                      translationEntry[0]
                                    )
                                  }
                                >
                                  <DeleteOutlined />
                                </Button>
                                <Title level={3}>{translationMeta.name}</Title>
                                <Text>{translationMeta.title}</Text>
                                <Input
                                  className={mainStyles.formItem}
                                  value={translationEntry[1].name}
                                  placeholder={translationMeta.placeholder}
                                  onChange={(event) =>
                                    this.handleTranslationChange(
                                      translationEntry[0],
                                      event.target.value
                                    )
                                  }
                                />
                              </Card>
                            );
                          }
                        }
                      )}
                      {this.state.isInLanguageSelectionMode && (
                        <>
                          <Title level={4}>Select a language</Title>
                          <Select
                            placeholder="Please select a language"
                            className={mainStyles.formSelect}
                            onSelect={this.handleLanguageSelect}
                          >
                            {Array.from(translationMetaData.entries()).map(
                              (translationMetaData) => {
                                return (
                                  <Option
                                    key={translationMetaData[0]}
                                    value={translationMetaData[0]}
                                    className={mainStyles.formSelect}
                                    disabled={this.state.translations.has(
                                      translationMetaData[0]
                                    )}
                                  >
                                    {translationMetaData[1].name}
                                  </Option>
                                );
                              }
                            )}
                          </Select>
                        </>
                      )}
                      {!this.state.isInLanguageSelectionMode &&
                        this.state.translations.size !=
                          translationMetaData.size && (
                          <Button
                            className={mainStyles.addTranslation}
                            type="link"
                            onClick={this.switchToLanguageSelectionMode}
                          >
                            <PlusOutlined />
                            Add a Translation
                          </Button>
                        )}
                      <Form.Item className={mainStyles.saveButton}>
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

export default withRouter(UpsertCategory);

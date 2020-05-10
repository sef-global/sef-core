import React from 'react';
import 'antd/dist/antd.less';
import RouteWithSubRoutes from '../RouteWithSubRoutes';
import { BrowserRouter as Router, Switch, Redirect } from 'react-router-dom';
import axios from 'axios';
import { SingleRoute } from '../../index';
import { notification, Spin } from 'antd';
import styles from './styles.css';

interface AppProps {
  routes: SingleRoute[];
}

interface StateProps {
  isLoading: boolean;
}

class App extends React.Component<AppProps, StateProps> {
  constructor(props: AppProps) {
    super(props);
    this.state = {
      isLoading: true,
    };
  }

  componentDidMount(): void {
    this.checkUserLoggedIn();
  }

  checkUserLoggedIn = () => {
    const pageURL = window.location.pathname;
    const lastURLSegment = pageURL.substr(pageURL.lastIndexOf('/') + 1);
    axios
      .post(window.location.origin + '/core/user/me')
      .then((result) => {
        if (result.status === 200) {
          if (lastURLSegment == 'login') {
            window.location.href = '/dashboard';
          }
          this.setState({
            isLoading: false,
          });
        }
      })
      .catch((error) => {
        if (error.hasOwnProperty('response') && error.response.status === 403) {
          if (lastURLSegment != 'login') {
            window.location.href = '/dashboard/login';
          }
        } else {
          notification.error({
            message: 'There was a problem',
            duration: 10,
            description: error.response.statusText,
          });
        }
        this.setState({
          isLoading: false,
        });
      });
  };

  render() {
    return this.state.isLoading ? (
      <Spin className={styles.spinner} size="large" tip="Loading..." />
    ) : (
      <Router>
        <Switch>
          <Redirect exact from="/dashboard" to="/dashboard/home" />
          {this.props.routes.map((route: SingleRoute) => (
            <RouteWithSubRoutes key={route.path} {...route} />
          ))}
        </Switch>
      </Router>
    );
  }
}

export default App;

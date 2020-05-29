import React from 'react';
import { Route } from 'react-router-dom';
import { RouteWithSubRoutesProps } from './interfaces';

class RouteWithSubRoutes extends React.Component<RouteWithSubRoutesProps> {
  render() {
    return (
      <Route
        path={this.props.path}
        exact={this.props.exact}
        render={(props) => (
          <this.props.component
            {...props}
            {...this.props}
            routes={this.props.routes}
          />
        )}
      />
    );
  }
}

export default RouteWithSubRoutes;

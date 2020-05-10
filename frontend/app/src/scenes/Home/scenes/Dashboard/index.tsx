import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

class Dashboard extends React.Component {
  render() {
    return (
      <div style={{ padding: 24 }}>
        <Title>Dashboard</Title>
      </div>
    );
  }
}

export default Dashboard;

import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './components/App';
import * as serviceWorker from './services/serviceWorkers/serviceWorker';
import Login from './scenes/Login';
import Home from './scenes/Home';
import Dashboard from './scenes/Home/scenes/Dashboard';
import Universities from './scenes/Home/scenes/Universities';
import Ambassadors from './scenes/Home/scenes/Ambassadors';
import AddUniversity from './scenes/Home/scenes/Universities/scenes/AddUniversity';
import AddAmbassador from './scenes/Home/scenes/Ambassadors/scenes/AddAmbassador';
import { SingleRoute } from './interfaces';

const routes: SingleRoute[] = [
  {
    path: '/dashboard/login',
    exact: true,
    component: Login,
  },
  {
    path: '/dashboard',
    exact: false,
    component: Home,
    routes: [
      {
        path: '/dashboard/home',
        exact: true,
        component: Dashboard,
      },
      {
        path: '/dashboard/universities',
        exact: true,
        component: Universities,
      },
      {
        path: '/dashboard/ambassadors',
        exact: true,
        component: Ambassadors,
      },
      {
        path: '/dashboard/universities/add',
        exact: true,
        component: AddUniversity,
      },
      {
        path: '/dashboard/ambassadors/add',
        exact: true,
        component: AddAmbassador,
      },
    ],
  },
];

ReactDOM.render(<App routes={routes} />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

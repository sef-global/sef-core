import { SingleRoute } from '../../interfaces';
import React from 'react';

export interface RouteWithSubRoutesProps {
  path: string;
  exact: boolean;
  routes?: SingleRoute[];
  component: React.ElementRef<any>;
  key?: string;
}

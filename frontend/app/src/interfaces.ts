export interface SingleRoute {
  path: string;
  exact: boolean;
  component: any;
  routes?: SingleRoute[];
}

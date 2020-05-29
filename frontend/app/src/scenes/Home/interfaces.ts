import { SingleRoute } from '../../interfaces';

export interface HomeProps {
  routes: SingleRoute[];
}

export interface University {
  id: number;
  name: string;
  imageUrl: string;
}

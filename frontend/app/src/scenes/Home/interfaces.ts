import { SingleRoute } from '../../interfaces';

export interface HomeProps {
  routes: SingleRoute[];
}

export interface University {
  id: number;
  name: string;
  imageUrl: string;
}

export interface Translation {
  name: string;
  language: string;
}

export interface Category {
  id: number;
  translations: Translation[];
}

export interface SubCategory {
  id: number;
  translations: Translation[];
}

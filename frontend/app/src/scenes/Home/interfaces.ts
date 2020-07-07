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

export interface ItemTranslation extends Translation {
  description: string;
}

export interface Category {
  id: number;
  name: string;
  translations: Translation[];
}

export interface SubCategory {
  id: number;
  name: string;
  translations: Translation[];
}

export interface Item {
  id: number;
  name: string;
  link: string;
  translations: ItemTranslation[];
}

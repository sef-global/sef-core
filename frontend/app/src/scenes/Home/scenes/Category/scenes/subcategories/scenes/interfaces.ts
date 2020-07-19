import { Item, SubCategory } from '../../../../../interfaces';

export interface ItemStateProps {
  items: Item[];
  isLoading: boolean;
  pagination: {
    current: number,
    pageSize: number,
    total: number,
  };
  subCategory: SubCategory | null;
}

export interface ItemPayload {
  content: Item[];
  totalElements: number;
}

export interface ItemUrlParams {
  subCategoryId: string;
}

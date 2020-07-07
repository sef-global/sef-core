import { Item } from '../../../../../interfaces';

export interface ItemStateProps {
  items: Item[];
  isLoading: boolean;
  pagination: {
    current: number,
    pageSize: number,
    total: number,
  };
}

export interface ItemPayload {
  content: Item[];
  totalElements: number;
}

export interface ItemUrlParams {
  subCategoryId: string;
}

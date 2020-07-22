import { Category } from '../../interfaces';

export interface AddCategoryStateProps {
  isLoading: boolean;
  category: Category | null;
}

export interface CategoryUrlParams {
  categoryId: string;
}

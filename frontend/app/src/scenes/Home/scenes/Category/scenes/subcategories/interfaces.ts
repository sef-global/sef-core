import { Category, SubCategory } from '../../../../interfaces';

export interface CategoryStateProps {
  isLoading: boolean;
  subCategories: SubCategory[];
  category: Category | null;
}

export interface CategoryUrlParams {
  categoryId: string;
}

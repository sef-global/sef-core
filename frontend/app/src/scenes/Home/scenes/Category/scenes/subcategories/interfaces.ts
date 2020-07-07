import { SubCategory } from '../../../../interfaces';

export interface CategoryStateProps {
  isLoading: boolean;
  subCategories: SubCategory[];
}

export interface CategoryUrlParams {
  categoryId: string;
}

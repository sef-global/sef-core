import { Category, SubCategory } from '../../interfaces';

export interface AddSubCategoryStateProps {
  isLoading: boolean;
  categories: Category[];
  subcategory: SubCategory | null;
}

export interface SubCategoryUrlParams {
  subCategoryId: string;
}

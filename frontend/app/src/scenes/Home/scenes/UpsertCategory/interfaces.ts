import { Category } from '../../interfaces';

export interface AddCategoryStateProps {
  isLoading: boolean;
  isInLanguageSelectionMode: boolean;
  category: Category | null;
  translations: Map<string, Translation>;
}

export interface CategoryUrlParams {
  categoryId: string;
}

export interface Translation {
  name: string;
}

export interface TranslationMetaData {
  name: string;
  placeholder: string;
  title: string;
}

import { SubCategory } from '../../interfaces';

export interface AddItemStateProps {
  isLoading: boolean;
  subCategories: SubCategory[];
}

export interface AddItemPayload {
  id: number;
}

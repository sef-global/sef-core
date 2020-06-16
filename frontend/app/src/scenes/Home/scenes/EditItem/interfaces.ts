import { Item, SubCategory } from '../../interfaces';

export interface EditItemStateProps {
  isLoading: boolean;
  subCategories: SubCategory[];
  item: Item | null;
}

import { Item, SubCategory } from '../../interfaces';

export interface AddItemStateProps {
  isLoading: boolean;
  subCategories: SubCategory[];
  item: Item | null;
}

export interface ItemUrlParams {
  itemId: string;
}

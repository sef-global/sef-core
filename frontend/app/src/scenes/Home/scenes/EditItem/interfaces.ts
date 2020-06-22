import { Item } from '../../interfaces';

export interface EditItemStateProps {
  isLoading: boolean;
  item: Item | null;
}

export interface ItemUrlParams {
  id: string;
}

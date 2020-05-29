import { University } from '../../../../../../interfaces';

export interface SearchUniversityProps {
  isVisible: boolean;
  onSelect: (university: University) => void;
  onCancel: () => void;
}

export interface SearchUniversityStateProps {
  universities: University[];
  isLoading: boolean;
}

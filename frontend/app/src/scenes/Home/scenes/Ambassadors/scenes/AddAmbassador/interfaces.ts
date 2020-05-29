import { University } from '../../../../interfaces';

export interface AddAmbassadorStateProps {
  isLoading: boolean;
  university: University | null;
  isSearchUniversityModalVisible: boolean;
}

export interface AddAmbassadorPayload {
  id: number;
}

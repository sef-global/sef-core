export interface Ambassador {
  id: number;
  firstName: string;
  lastName: string;
  university: string;
}

export interface AmbassadorsStateProps {
  ambassadors: Ambassador[];
  isLoading: boolean;
  pagination: {
    current: number,
    pageSize: number,
    total: number,
  };
}

export interface AmbassadorPayload {
  content: Ambassador[];
  totalElements: number;
}

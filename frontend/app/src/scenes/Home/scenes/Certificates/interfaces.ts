import { Certificate } from '../../interfaces';

export interface CertificatesStateProps {
  certificates: Certificate[];
  isLoading: boolean;
  pagination: {
    current: number,
    pageSize: number,
    total: number,
  };
}

export interface CertificatesPayload {
  content: Certificate[];
  totalElements: number;
}

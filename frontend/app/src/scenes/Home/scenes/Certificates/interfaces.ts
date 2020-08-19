import { Certificate } from '../../interfaces';

export interface CertificatesStateProps {
  certificates: Certificate[];
  isLoading: boolean;
}

export interface CertificatesPayload {
  content: Certificate[];
}

import { Certificate } from '../../interfaces';

export interface UpsertCertificateStateProps {
  isLoading: boolean;
  certificate: Certificate | null;
}

export interface CertificateUrlParams {
  id: string;
}

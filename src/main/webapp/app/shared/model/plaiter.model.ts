import { IPlaiterConfiguration } from 'app/shared/model/plaiter-configuration.model';

export interface IPlaiter {
  id?: number;
  index?: number;
  totalBobinsCount?: number;
  plaiterConfigurations?: IPlaiterConfiguration[] | null;
}

export const defaultValue: Readonly<IPlaiter> = {};

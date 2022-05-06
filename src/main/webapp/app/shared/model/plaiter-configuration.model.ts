import { IPlaiter } from 'app/shared/model/plaiter.model';

export interface IPlaiterConfiguration {
  id?: number;
  usedBobinsCount?: number;
  plaiter?: IPlaiter;
}

export const defaultValue: Readonly<IPlaiterConfiguration> = {};

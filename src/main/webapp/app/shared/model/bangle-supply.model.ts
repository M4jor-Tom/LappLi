import { IBangle } from 'app/shared/model/bangle.model';

export interface IBangleSupply {
  id?: number;
  apparitions?: number;
  bangle?: IBangle;
}

export const defaultValue: Readonly<IBangleSupply> = {};

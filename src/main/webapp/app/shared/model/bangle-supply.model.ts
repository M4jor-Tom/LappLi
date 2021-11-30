import { IBangle } from 'app/shared/model/bangle.model';
import { IAbstractLiftedSupply } from './abstract-lifted-supply.model';

export interface IBangleSupply extends IAbstractLiftedSupply {
  id?: number;
  bangle?: IBangle;
}

export const defaultValue: Readonly<IBangleSupply> = {};

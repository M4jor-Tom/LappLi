import { IBangle } from 'app/shared/model/bangle.model';
import { IStrand } from 'app/shared/model/strand.model';

export interface IBangleSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  bangle?: IBangle;
  strand?: IStrand;
}

export const defaultValue: Readonly<IBangleSupply> = {};

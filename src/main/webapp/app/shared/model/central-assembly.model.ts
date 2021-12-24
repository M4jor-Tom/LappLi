import { IStrand } from 'app/shared/model/strand.model';

export interface ICentralAssembly {
  id?: number;
  productionStep?: number;
  strand?: IStrand;
}

export const defaultValue: Readonly<ICentralAssembly> = {};

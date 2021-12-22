import { IStrand } from 'app/shared/model/strand.model';

export interface IIntersticeAssembly {
  id?: number;
  productionStep?: number;
  strand?: IStrand;
}

export const defaultValue: Readonly<IIntersticeAssembly> = {};

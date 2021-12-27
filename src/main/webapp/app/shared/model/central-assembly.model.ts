import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractAssembly } from './abstract-assembly.model';

export interface ICentralAssembly extends IAbstractAssembly {
  id?: number;
  productionStep?: number;
  strand?: IStrand;
}

export const defaultValue: Readonly<ICentralAssembly> = {};

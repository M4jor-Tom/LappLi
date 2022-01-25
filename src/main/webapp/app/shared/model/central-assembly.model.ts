import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractAssembly } from './abstract-assembly.model';
import { ISupplyPosition } from './supply-position.model';

export interface ICentralAssembly extends IAbstractAssembly {
  id?: number;
  supplyPosition?: ISupplyPosition;
  //  productionStep?: number;
}

export const defaultValue: Readonly<ICentralAssembly> = {};

import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractAssembly } from './abstract-assembly.model';
import { IPosition } from './position.model';

export interface ICentralAssembly extends IAbstractAssembly {
  //  id?: number;
  //  productionStep?: number;
  position?: IPosition;
}

export const defaultValue: Readonly<ICentralAssembly> = {};

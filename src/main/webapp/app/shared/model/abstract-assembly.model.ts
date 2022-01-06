import { IAbstractOperation } from './abstract-operation.model';
import { IPosition } from './position.model';
import { IStrand } from './strand.model';

export interface IAbstractAssembly extends IAbstractOperation {
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};

import { IAbstractOperation } from './abstract-operation.model';
import { IPosition } from './position.model';

export interface IAbstractAssembly extends IAbstractOperation {
  operationLayer?: number;
  productionStep?: number;
  position?: IPosition;
  designation?: string | null;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};

import { IAbstractOperation } from './abstract-operation.model';
import { IStrand } from './strand.model';

export interface IAbstractAssembly extends IAbstractOperation {
  assemblyLayer?: number;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};

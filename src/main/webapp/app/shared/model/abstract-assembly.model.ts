import { IAbstractOperation } from './abstract-operation.model';

export interface IAbstractAssembly extends IAbstractOperation {
  assemblyLayer?: number;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};

import { IAbstractOperation } from './abstract-operation.model';

export interface IAssemblableOperation extends IAbstractOperation {
  diameterAssemblyStep?: number;
}

export function isAssemblableOperation(object: IAbstractOperation): object is IAssemblableOperation {
  return Object.prototype.hasOwnProperty.call(object, 'diameterAssemblyStep');
}

export const defaultValue: Readonly<IAssemblableOperation> = {};

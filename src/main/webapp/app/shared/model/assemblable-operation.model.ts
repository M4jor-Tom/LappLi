import { IAbstractOperation } from './abstract-operation.model';

export interface IAssemblableOperation extends IAbstractOperation {
  diameterAssemblyStep?: number;
  mullerStandardizedFormatDiameterAssemblyStep?: string;
}

export function isAssemblableOperation(object: IAbstractOperation): object is IAssemblableOperation {
  return (
    Object.prototype.hasOwnProperty.call(object, 'diameterAssemblyStep') &&
    Object.prototype.hasOwnProperty.call(object, 'mullerStandardizedFormatDiameterAssemblyStep')
  );
}

export const defaultValue: Readonly<IAssemblableOperation> = {};

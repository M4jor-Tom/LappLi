import { IAbstractOperation } from './abstract-operation.model';
import { IAssemblableOperation, isAssemblableOperation } from './assemblable-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IMeanedAssemblableOperation extends IAssemblableOperation {
  assemblyMean?: AssemblyMean;
}

export function isMeanedAssemblableOperation(object: IAbstractOperation): object is IMeanedAssemblableOperation {
  return isAssemblableOperation(object) && Object.prototype.hasOwnProperty.call(object, 'assemblyMean');
}

export const defaultValue: Readonly<IMeanedAssemblableOperation> = {};

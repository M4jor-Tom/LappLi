import { IAssemblableOperation } from './assemblable-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IMeanedAssemblableOperation extends IAssemblableOperation {
  assemblyMean?: AssemblyMean;
}

export const defaultValue: Readonly<IMeanedAssemblableOperation> = {};

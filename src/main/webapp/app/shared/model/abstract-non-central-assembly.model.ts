import { IAbstractAssembly } from './abstract-assembly.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IAbstractNonCentralAssembly extends IAbstractAssembly {
  assemblyStep?: number;
  assemblyMean?: AssemblyMean;
}

export const defaultValue: Readonly<IAbstractNonCentralAssembly> = {};

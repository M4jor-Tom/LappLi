import { IAbstractAssembly } from './abstract-assembly.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';
import { IPosition } from './position.model';

export interface IAbstractNonCentralAssembly extends IAbstractAssembly {
  diameterAssemblyStep?: number;
  positions?: IPosition[];
  assemblyMean?: AssemblyMean;
}

export const defaultValue: Readonly<IAbstractNonCentralAssembly> = {};

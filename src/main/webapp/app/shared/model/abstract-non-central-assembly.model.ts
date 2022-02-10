import { IAbstractAssembly } from './abstract-assembly.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IAbstractNonCentralAssembly extends IAbstractAssembly {
  diameterAssemblyStep?: number;
  assemblyMean?: AssemblyMean;
  forcedMeanMilimeterComponentDiameter?: number;
  componentsCount?: number;
}

export const defaultValue: Readonly<IAbstractNonCentralAssembly> = {};

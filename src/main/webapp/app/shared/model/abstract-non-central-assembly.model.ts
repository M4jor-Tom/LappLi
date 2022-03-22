import { IAbstractAssembly } from './abstract-assembly.model';
import { IAbstractOperation } from './abstract-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IAbstractNonCentralAssembly extends IAbstractAssembly {
  diameterAssemblyStep?: number;
  assemblyMean?: AssemblyMean;
  forcedMeanMilimeterComponentDiameter?: number;
  componentsCount?: number;
}

export function isNonCentralAssembly(object: IAbstractOperation): object is IAbstractNonCentralAssembly {
  return (
    Object.prototype.hasOwnProperty.call(object, 'diameterAssemblyStep') &&
    Object.prototype.hasOwnProperty.call(object, 'assemblyMean') &&
    Object.prototype.hasOwnProperty.call(object, 'forcedMeanMilimeterComponentDiameter') &&
    Object.prototype.hasOwnProperty.call(object, 'componentsCount')
  );
}

export const defaultValue: Readonly<IAbstractNonCentralAssembly> = {};

import { IAbstractAssembly, isAssembly } from './abstract-assembly.model';
import { IAbstractOperation } from './abstract-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';
import { IMeanedAssemblableOperation, isMeanedAssemblableOperation } from './meaned-assemblable-operation.model';

export interface IAbstractNonCentralAssembly extends IAbstractAssembly, IMeanedAssemblableOperation {
  forcedMeanMilimeterComponentDiameter?: number;
  componentsCount?: number;
}

export function isNonCentralAssembly(object: IAbstractOperation): object is IAbstractNonCentralAssembly {
  return (
    isMeanedAssemblableOperation(object) &&
    isAssembly(object) &&
    Object.prototype.hasOwnProperty.call(object, 'forcedMeanMilimeterComponentDiameter') &&
    Object.prototype.hasOwnProperty.call(object, 'componentsCount')
  );
}

export const defaultValue: Readonly<IAbstractNonCentralAssembly> = {};

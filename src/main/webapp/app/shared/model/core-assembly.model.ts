import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractNonCentralAssembly } from './abstract-non-central-assembly.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ICoreAssembly extends IAbstractNonCentralAssembly {
  id?: number;
  milimeterAssemblyVoid?: number;
  mullerStandardizedFormatMilimeterAssemblyVoid?: number;
}

export function isCoreAssembly(object: IAbstractOperation): object is ICoreAssembly {
  return (
    Object.prototype.hasOwnProperty.call(object, 'milimeterAssemblyVoid') &&
    Object.prototype.hasOwnProperty.call(object, 'mullerStandardizedFormatMilimeterAssemblyVoid')
  );
}

export const defaultValue: Readonly<ICoreAssembly> = {};

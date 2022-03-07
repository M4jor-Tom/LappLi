import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractNonCentralAssembly } from './abstract-non-central-assembly.model';

export interface ICoreAssembly extends IAbstractNonCentralAssembly {
  id?: number;
  milimeterAssemblyVoid?: number;
  mullerStandardizedFormatMilimeterAssemblyVoid?: number;
}

export const defaultValue: Readonly<ICoreAssembly> = {};

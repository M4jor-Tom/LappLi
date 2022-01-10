import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractNonCentralAssembly } from './abstract-non-central-assembly.model';

export interface ICoreAssembly extends IAbstractNonCentralAssembly {
  id?: number;
}

export const defaultValue: Readonly<ICoreAssembly> = {};

import { IStrand } from 'app/shared/model/strand.model';
import { IAbstractNonCentralAssembly } from './abstract-non-central-assembly.model';
import { ISupplyPosition } from './supply-position.model';

export interface IIntersticeAssembly extends IAbstractNonCentralAssembly {
  id?: number;
  supplyPosition?: ISupplyPosition;
  intersticeLayer?: number;
}

export const defaultValue: Readonly<IIntersticeAssembly> = {};

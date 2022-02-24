import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { IAbstractAssembly } from './abstract-assembly.model';

export interface ICentralAssembly extends IAbstractAssembly {
  id?: number;
  supplyPosition?: ISupplyPosition | null;
}

export const defaultValue: Readonly<ICentralAssembly> = {};

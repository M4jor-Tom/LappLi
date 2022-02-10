import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { IStrand } from 'app/shared/model/strand.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { IAbstractSupply } from './abstract-supply.model';

export interface ISupplyPosition {
  id?: number;
  supplyApparitionsUsage?: number;
  supply?: IAbstractSupply | null;
  ownerCentralAssembly?: ICentralAssembly | null;
  elementSupply?: IElementSupply | null;
  bangleSupply?: IBangleSupply | null;
  customComponentSupply?: ICustomComponentSupply | null;
  oneStudySupply?: IOneStudySupply | null;
  ownerStrand?: IStrand | null;
  ownerIntersticeAssembly?: IIntersticeAssembly | null;
}

export const defaultValue: Readonly<ISupplyPosition> = {};

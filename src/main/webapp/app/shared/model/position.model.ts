import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';

export interface IPosition {
  id?: number;
  value?: number;
  elementSupply?: IElementSupply | null;
  bangleSupply?: IBangleSupply | null;
  customComponentSupply?: ICustomComponentSupply | null;
  oneStudySupply?: IOneStudySupply | null;
  ownerCentralAssembly?: ICentralAssembly;
  ownerCoreAssembly?: ICoreAssembly;
  ownerIntersticeAssembly?: IIntersticeAssembly;
}

export const defaultValue: Readonly<IPosition> = {};

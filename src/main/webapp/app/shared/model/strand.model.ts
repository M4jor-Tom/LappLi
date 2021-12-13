import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { OperationType } from 'app/shared/model/enumerations/operation-type.model';

export interface IStrand {
  id?: number;
  designation?: string;
  housingOperationType?: OperationType | null;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
}

export const defaultValue: Readonly<IStrand> = {};

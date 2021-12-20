import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';

export interface IStrand {
  id?: number;
  designation?: string;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
  oneStudySupplies?: IOneStudySupply[] | null;
}

export const defaultValue: Readonly<IStrand> = {};

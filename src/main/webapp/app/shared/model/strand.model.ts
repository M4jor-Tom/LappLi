import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { IStudy } from 'app/shared/model/study.model';

export interface IStrand {
  id?: number;
  designation?: string;
  productionStep?: number;
  supplyPositions?: ISupplyPosition[] | null;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
  oneStudySupplies?: IOneStudySupply[] | null;
  suppliesCountsCommonDividers?: number[];
  undividedSuppliedComponentsCount?: number;
  futureStudy?: IStudy;
}

export const defaultValue: Readonly<IStrand> = {};

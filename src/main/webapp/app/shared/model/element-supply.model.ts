import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';
import { IAbstractLiftedSupply } from './abstract-lifted-supply.model';

export interface IElementSupply extends IAbstractLiftedSupply {
  id?: number;
  markingType?: MarkingType;
  metricQuantity?: number;
  element?: IElement;
  bestLiftersNames?: string;
  markingTechnique?: MarkingTechnique | null;

  insulationMaterialDesignation?: string;
}

export const defaultValue: Readonly<IElementSupply> = {};

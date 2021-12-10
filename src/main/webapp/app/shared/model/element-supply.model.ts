import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';
import { IAbstractMarkedLiftedSupply } from './abstract-marked-lifted-supply.model';

export interface IElementSupply extends IAbstractMarkedLiftedSupply {
  id?: number;
  metricQuantity?: number;
  element?: IElement;
}

export const defaultValue: Readonly<IElementSupply> = {};

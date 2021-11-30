import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';
import { IAbstractSupply } from './abstract-supply.model';

export interface IAbstractLiftedSupply extends IAbstractSupply {
  bestLiftersNames?: string;
}

export const defaultValue: Readonly<IAbstractLiftedSupply> = {};

import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';
import { IAbstractLiftedSupply } from './abstract-lifted-supply.model';

export interface IAbstractMarkedLiftedSupply extends IAbstractLiftedSupply {
  markingType?: MarkingType;
  markingTechnique?: MarkingTechnique | null;
}

export const defaultValue: Readonly<IAbstractMarkedLiftedSupply> = {};

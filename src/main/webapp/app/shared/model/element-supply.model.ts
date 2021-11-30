import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export interface IElementSupply {
  id?: number;
  apparitions?: number;
  forcedMarking?: string | null;
  markingType?: MarkingType | null;
  quantity?: number;
  element?: IElement;
  bestLiftersNames?: string;
  markingTechnique?: MarkingTechnique | null;
  hourPreparationTime?: number;
  hourExecutionTime?: number;
  meterPerSecondSpeed?: number;

  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
  insulationMaterialDesignation?: string;
}

export const defaultValue: Readonly<IElementSupply> = {};

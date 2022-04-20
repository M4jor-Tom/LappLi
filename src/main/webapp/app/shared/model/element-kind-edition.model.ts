import dayjs from 'dayjs';
import { IElementKind } from 'app/shared/model/element-kind.model';

export interface IElementKindEdition {
  id?: number;
  editionDateTime?: string;
  newGramPerMeterLinearMass?: number | null;
  newMilimeterDiameter?: number | null;
  newMilimeterInsulationThickness?: number | null;
  editedElementKind?: IElementKind;
}

export const defaultValue: Readonly<IElementKindEdition> = {};

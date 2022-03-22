import { ITapeKind } from 'app/shared/model/tape-kind.model';

export interface ITape {
  id?: number;
  number?: number;
  designation?: string;
  milimeterWidth?: number;
  milimeterDiameterIncidency?: number;
  tapeKind?: ITapeKind;
}

export const defaultValue: Readonly<ITape> = {};

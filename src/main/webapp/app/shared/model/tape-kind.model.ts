export interface ITapeKind {
  id?: number;
  targetCoveringRate?: number;
  designation?: string;
}

export const defaultValue: Readonly<ITapeKind> = {};

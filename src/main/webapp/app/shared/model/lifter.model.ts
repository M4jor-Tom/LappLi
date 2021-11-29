export interface ILifter {
  id?: number;
  index?: number;
  minimumMilimeterDiameter?: number;
  maximumMilimeterDiameter?: number;
  supportsSpirallyColoredMarkingType?: boolean;
  supportsLongitudinallyColoredMarkingType?: boolean;
  supportsNumberedMarkingType?: boolean;
  name?: string;
  supportsInkJetMarkingTechnique?: boolean;
  supportsRsdMarkingTechnique?: boolean;
}

export const defaultValue: Readonly<ILifter> = {
  supportsSpirallyColoredMarkingType: false,
  supportsLongitudinallyColoredMarkingType: false,
  supportsNumberedMarkingType: false,
  supportsInkJetMarkingTechnique: false,
  supportsRsdMarkingTechnique: false,
};

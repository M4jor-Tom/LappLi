export interface ILifter {
  id?: number;
  index?: number;
  minimumMilimeterDiameter?: number;
  maximumMilimeterDiameter?: number;
  supportsSpirallyColoredMarkingType?: boolean;
  supportsLongitudinallyColoredMarkingType?: boolean;
  supportsNumberedMarkingType?: boolean;
}

export const defaultValue: Readonly<ILifter> = {
  supportsSpirallyColoredMarkingType: false,
  supportsLongitudinallyColoredMarkingType: false,
  supportsNumberedMarkingType: false,
};

export interface ICopperFiber {
  id?: number;
  number?: number | null;
  designation?: string | null;
  copperIsRedNotTinned?: boolean;
  milimeterDiameter?: number;
}

export const defaultValue: Readonly<ICopperFiber> = {
  copperIsRedNotTinned: false,
};

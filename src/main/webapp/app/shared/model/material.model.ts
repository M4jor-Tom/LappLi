export interface IMaterial {
  id?: number;
  number?: number;
  designation?: string;
  isMarkable?: boolean;
}

export const defaultValue: Readonly<IMaterial> = {
  isMarkable: false,
};

import type {ISeries} from "./SeriesModel";

export interface IClipperList{
    clippers: Array<IClipper>
}

export interface IClipper{
    id: string;
    name: string;
    series: ISeries;
    seriesNumber: string;
    createdById: string;
}
import type {ISeries} from "./SeriesModel";

export interface IClipper{
    id: string;
    name: string;
    series: ISeries;
    seriesNumber: string;
    imageData: string;
    createdBy: string;
}

export interface IClipperList{
    clippers: Array<IClipper>
}

export interface IClipperCreateRequest{
    id: string
    name: string;
    seriesNumber: string;
    seriesId: string;
    createdBy: string;
}

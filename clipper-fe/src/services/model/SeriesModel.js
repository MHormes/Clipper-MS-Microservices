import type {IClipperList} from "./ClipperModel";

export interface ISeries{
    id: string;
    name: string;
    clippers: IClipperList[];
    imageData: string;
    custom: boolean;
    createdBy: string;
}

export interface ISeriesCreateRequest{
    name: string;
    imageData: string;
    custom: boolean;
    createdBy: string;
}
import type {IClipperList} from "./ClipperModel";

export interface ISeries{
    id: string;
    name: string;
    clippers: IClipperList[];
    custom: boolean;
    createdBy: string;
}
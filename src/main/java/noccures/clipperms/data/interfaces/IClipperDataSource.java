package noccures.clipperms.data.interfaces;

import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;

public interface IClipperDataSource {

    Clipper saveClipper(Clipper clipperToAdd);

    Series getExistingSeriesForNewClipper(String seriesId);

    Clipper getClipperWithId(String id);

    Clipper updateClipper(Clipper clipperWithUpdate);

    Clipper deleteClipper(String clipperId);
}


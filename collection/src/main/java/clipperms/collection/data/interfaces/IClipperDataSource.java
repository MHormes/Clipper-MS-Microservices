package clipperms.collection.data.interfaces;

import clipperms.collection.model.Clipper;
import clipperms.collection.model.Series;

import java.util.List;
import java.util.UUID;

public interface IClipperDataSource {

    Clipper saveClipper(Clipper clipperToAdd);

    Series getExistingSeriesForNewClipper(UUID seriesId);

    Clipper getClipperWithId(UUID id);

    List<Clipper> getAllClippers();

    Clipper updateClipper(Clipper clipperWithUpdate);

    Clipper deleteClipper(UUID clipperId);
}


package noccures.clipperms.data.interfaces;

import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.CollectedClipper;
import noccures.clipperms.model.Series;

import java.util.List;
import java.util.UUID;

public interface ICollectedClipperDataSource {

    CollectedClipper addToCollection(CollectedClipper collectedClipperToAdd);

    CollectedClipper getCollectedClipperWithId(UUID id);

    List<CollectedClipper> getCollectedClippersForClipperId(UUID clipperId);

    CollectedClipper updateClipper(CollectedClipper clipperWithUpdate);

    CollectedClipper removeFromCollection(UUID collectedClipperId);
}

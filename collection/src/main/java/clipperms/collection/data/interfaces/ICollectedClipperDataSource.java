package clipperms.collection.data.interfaces;

import clipperms.collection.model.CollectedClipper;

import java.util.List;
import java.util.UUID;

public interface ICollectedClipperDataSource {

    CollectedClipper addToCollection(CollectedClipper collectedClipperToAdd);

    CollectedClipper getCollectedClipperWithId(UUID id);

    List<CollectedClipper> getCollectedClippersForClipperId(UUID clipperId);

    CollectedClipper updateClipper(CollectedClipper clipperWithUpdate);

    CollectedClipper removeFromCollection(UUID collectedClipperId);
    CollectedClipper removeFromCollection(UUID clipperId, UUID userId);
}

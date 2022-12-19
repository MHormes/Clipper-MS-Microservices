package noccures.clipperms.data;

import noccures.clipperms.data.interfaces.ICollectedClipperDataSource;
import noccures.clipperms.data.repositories.ICollectedClipperRepository;
import noccures.clipperms.model.CollectedClipper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CollectedClipperDataSource implements ICollectedClipperDataSource {

    ICollectedClipperRepository collectedClipperRepo;

    public CollectedClipperDataSource(ICollectedClipperRepository collectedClipperRepository){
        collectedClipperRepo = collectedClipperRepository;
    }

    @Override
    public CollectedClipper addToCollection(CollectedClipper collectedToAdd) {
        collectedClipperRepo.save(collectedToAdd);
        return getCollectedClipperWithId(collectedToAdd.getId());
    }

    @Override
    public CollectedClipper getCollectedClipperWithId(UUID id) {
        Optional<CollectedClipper> clipperWithId = collectedClipperRepo.findById(id);
        return clipperWithId.orElse(null);
    }

    @Override
    public List<CollectedClipper> getCollectedClippersForClipperId(UUID clipperId) {
        return collectedClipperRepo.getCollectedClippersByClipperIdId(clipperId);
    }

    @Override
    public CollectedClipper updateClipper(CollectedClipper collectedWithUpdate) {
        //Get clipper to update from DB
        CollectedClipper collectedToUpdate = getCollectedClipperWithId(collectedWithUpdate.getId());
        //Assign values from incoming object to database
        collectedToUpdate.setClipperId(collectedWithUpdate.getClipperId());
        collectedToUpdate.setUserId(collectedWithUpdate.getUserId());
        collectedToUpdate.setNotes(collectedWithUpdate.getNotes());
        collectedToUpdate.setLocationBought(collectedWithUpdate.getLocationBought());
        //Save into DB
        collectedClipperRepo.save(collectedToUpdate);
        //Return from database to ensure updates went successful
        return getCollectedClipperWithId(collectedWithUpdate.getId());
    }

    @Override
    public CollectedClipper removeFromCollection(UUID collectedClipperId) {
        collectedClipperRepo.deleteById(collectedClipperId);
        return getCollectedClipperWithId(collectedClipperId);
    }
}

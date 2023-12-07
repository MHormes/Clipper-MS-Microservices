package clipperms.collection.service;

import clipperms.collection.data.interfaces.ICollectedClipperDataSource;
import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.ExceptionMessages;
import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.CollectedClipper;
import clipperms.collection.service.interfaces.IClipperService;
import clipperms.collection.service.interfaces.ICollectedClipperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CollectedClipperService implements ICollectedClipperService {

    ICollectedClipperDataSource collectedClipperData;

    @Autowired
    public CollectedClipperService(ICollectedClipperDataSource collectedClipperData) {
        this.collectedClipperData = collectedClipperData;
    }

    @Override
    public CollectedClipper addCollectedClipper(CollectedClipper collectedToAdd) throws IncorrectInputException, DatabaseFailedOperationException {
       //todo check if clipper exists
        //add to DB
        var expectedResult = collectedClipperData.addToCollection(collectedToAdd);
        if (expectedResult == null) {
            log.error("Collected clipper could not be saved to database");
            throw new DatabaseFailedOperationException(ExceptionMessages.C_CLIPPER_GET_FAILED);
        }
        log.info("Collected clipper: {} saved to database", expectedResult);
        return expectedResult;
    }

    @Override
    public CollectedClipper getCollectedClipperWithId(String id) throws IncorrectInputException {
        var collectedWithId = collectedClipperData.getCollectedClipperWithId(UUID.fromString(id));
        if (collectedWithId == null) {
            log.error("Collected clipper with id {} not found", id);
            throw new IncorrectInputException(ExceptionMessages.C_CLIPPER_WITH_ID_NOT_FOUND + id);
        }
        log.info("Collected clipper with id {} found", id);
        return collectedWithId;
    }

    @Override
    public List<CollectedClipper> getCollectedClippersForClipperId(String clipperId){
        log.info("Getting collected clippers for clipper with id {}", clipperId);
        return collectedClipperData.getCollectedClippersForClipperId(UUID.fromString(clipperId));
    }


    @Override
    public CollectedClipper updateCollectedClipper(CollectedClipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException {
        log.info("Updating collected clipper with id {}", clipperWithUpdate.getId());
        return null;
    }

    @Override
    public void deleteCollectedClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException {
        //ensure collected clipper exists.
        getCollectedClipperWithId(clipperId);

        if (collectedClipperData.removeFromCollection(UUID.fromString(clipperId)) != null){
            log.error("Collected clipper with id {} could not be deleted", clipperId);
            throw new DatabaseFailedOperationException(ExceptionMessages.C_CLIPPER_PRESENT_AFTER_DELETE);
        }
        log.info("Deleting collected clipper with id {}", clipperId);
    }
}

package noccures.clipperms.service;

import noccures.clipperms.data.interfaces.ICollectedClipperDataSource;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.CollectedClipper;
import noccures.clipperms.service.interfaces.ICollectedClipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollectedClipperService implements ICollectedClipperService {

    @Autowired
    ICollectedClipperDataSource collectedClipperData;

    @Override
    public CollectedClipper addCollectedClipper(CollectedClipper collectedToAdd) throws IncorrectInputException, DatabaseFailedOperationException {
        //todo check collected validity
        //add to DB
        var expectedResult = collectedClipperData.addToCollection(collectedToAdd);
        if (expectedResult == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.C_CLIPPER_GET_FAILED);
        }
        return expectedResult;
    }

    @Override
    public CollectedClipper getCollectedClipperWithId(String id) throws IncorrectInputException {
        var collectedWithId = collectedClipperData.getCollectedClipperWithId(UUID.fromString(id));
        if (collectedWithId == null) {
            throw new IncorrectInputException(ExceptionMessages.C_CLIPPER_WITH_ID_NOT_FOUND + id);
        }
        return collectedWithId;
    }

    @Override
    public CollectedClipper updateCollectedClipper(CollectedClipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException {
        return null;
    }

    @Override
    public void deleteCollectedClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException {
        //ensure collected clipper exists.
        getCollectedClipperWithId(clipperId);

        if (collectedClipperData.removeFromCollection(UUID.fromString(clipperId)) != null){
            throw new DatabaseFailedOperationException(ExceptionMessages.C_CLIPPER_PRESENT_AFTER_DELETE);
        }
    }
}

package clipperms.collection.service.interfaces;

import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.CollectedClipper;

import java.util.List;

public interface ICollectedClipperService {

    CollectedClipper addCollectedClipper(CollectedClipper collectedToAdd) throws IncorrectInputException, DatabaseFailedOperationException;

    CollectedClipper getCollectedClipperWithId(String id) throws IncorrectInputException;

    List<CollectedClipper> getCollectedClippersForClipperId(String clipperId);

    CollectedClipper updateCollectedClipper(CollectedClipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException;

    void deleteCollectedClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException;
}

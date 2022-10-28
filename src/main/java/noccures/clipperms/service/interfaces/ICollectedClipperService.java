package noccures.clipperms.service.interfaces;

import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.CollectedClipper;

public interface ICollectedClipperService {

    CollectedClipper addCollectedClipper(CollectedClipper collectedToAdd) throws IncorrectInputException, DatabaseFailedOperationException;

    CollectedClipper getCollectedClipperWithId(String id) throws IncorrectInputException;

    CollectedClipper updateCollectedClipper(CollectedClipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException;

    void deleteCollectedClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException;
}

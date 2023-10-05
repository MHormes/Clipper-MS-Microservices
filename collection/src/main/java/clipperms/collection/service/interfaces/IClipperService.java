package clipperms.collection.service.interfaces;

import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.Clipper;

import java.util.List;

/**
 * Interface for ClipperService. Holds all clipper related logic.
 */
public interface IClipperService {

    Clipper addClipper(Clipper clipperToAdd, String seriesId) throws IncorrectInputException, DatabaseFailedOperationException;

    Clipper getClipperWithId(String id) throws IncorrectInputException;

    List<Clipper> getAllClippers();

    Clipper updateClipper(Clipper clipperWithUpdate, String seriesId) throws IncorrectInputException, DatabaseFailedOperationException;

    void deleteClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException;
}

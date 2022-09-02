package noccures.clipperms.service.interfaces;

import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;

public interface IClipperService {

    Clipper addClipper(Clipper clipperToAdd, String seriesId) throws IncorrectInputException, DatabaseFailedOperationException;

    Clipper getClipperWithId(String id) throws IncorrectInputException;

    Clipper updateClipper(Clipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException;

    void deleteClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException;
}

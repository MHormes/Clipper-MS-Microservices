package noccures.clipperms.service;

import noccures.clipperms.data.interfaces.IClipperDataSource;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.service.interfaces.IClipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClipperService implements IClipperService {

    IClipperDataSource clipperData;

    @Autowired
    public ClipperService(IClipperDataSource clipperDataSource) {
        clipperData = clipperDataSource;
    }


    //Method to add a new clipper
    public Clipper addClipper(Clipper clipperToAdd, String seriesId) throws IncorrectInputException, DatabaseFailedOperationException {
        //Check if name is not empty
        if (clipperToAdd.getName().isBlank()) {
            throw new IncorrectInputException(ExceptionMessages.CLIPPER_NO_NAME);
        }

        //separate id == null? -> not part of a series. so leave seriesId null -> is set when converting dto to model
        if (seriesId != null) {
            //Check if series exists
            var seriesObject = clipperData.getExistingSeriesForNewClipper(UUID.fromString(seriesId));
            if (seriesObject == null) {
                throw new IncorrectInputException(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + seriesId);
            } else {
                //Add series reference to clipper
                clipperToAdd.setSeriesId(seriesObject);
            }
        }
        //Add to DB
        var expectedResult = clipperData.saveClipper(clipperToAdd);
        if (expectedResult == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_GET_FAILED);
        }
        return expectedResult;
    }

    //method to get a single clipper based on id
    @Override
    public Clipper getClipperWithId(String id) throws IncorrectInputException {
        var clipperWithId = clipperData.getClipperWithId(UUID.fromString(id));
        if (clipperWithId == null) {
            throw new IncorrectInputException(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + id);
        }
        return clipperWithId;
    }

    //method to update clipper with new values based on id
    @Override
    public Clipper updateClipper(Clipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException {
        //make sure clipper with supplied id exists.
        getClipperWithId(clipperWithUpdate.getId().toString());
        var updatedClipper = clipperData.updateClipper(clipperWithUpdate);
        if (updatedClipper == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_GET_FAILED);
        }
        return updatedClipper;
    }

    //method to remove clipper based on id
    @Override
    public void deleteClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException {
        //ensure clipper with id exists
        getClipperWithId(clipperId);

        if (clipperData.deleteClipper(UUID.fromString(clipperId)) != null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_PRESENT_AFTER_DELETE);
        }
    }
}

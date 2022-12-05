package noccures.clipperms.service;

import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.interfaces.IClipperDataSource;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.CollectedClipper;
import noccures.clipperms.service.interfaces.IClipperService;
import noccures.clipperms.service.interfaces.ICollectedClipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClipperService implements IClipperService {

    IClipperDataSource clipperData;

    ICollectedClipperService collectedClipperService;

    @Autowired
    public ClipperService(IClipperDataSource clipperDataSource, ICollectedClipperService collectedClipperService) {
        clipperData = clipperDataSource;
        this.collectedClipperService = collectedClipperService;
    }


    //Method to add a new clipper
    public Clipper addClipper(Clipper clipperToAdd, String seriesId) throws IncorrectInputException, DatabaseFailedOperationException {
        //Check if name is not empty
        if (clipperToAdd.getName().isBlank()) {
            log.error("Clipper name is empty");
            throw new IncorrectInputException(ExceptionMessages.CLIPPER_NO_NAME);
        }

        //separate id == null? -> not part of a series. so leave seriesId null -> is set when converting dto to model
        if (seriesId != null) {
            //Check if series exists
            var seriesObject = clipperData.getExistingSeriesForNewClipper(UUID.fromString(seriesId));
            if (seriesObject == null) {
                log.error("Series with id {} does not exist", seriesId);
                throw new IncorrectInputException(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + seriesId);
            } else {
                //Add series reference to clipper
                clipperToAdd.setSeriesId(seriesObject);
            }
        }
        if (clipperToAdd.getId() == null) {
            clipperToAdd.setId(UUID.randomUUID());
        }
        //Add to DB
        var expectedResult = clipperData.saveClipper(clipperToAdd);
        if (expectedResult == null) {
            log.error("Clipper could not be saved to database");
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_GET_FAILED);
        }
        log.info("Clipper {} saved to database", clipperToAdd.getName());
        return expectedResult;
    }

    //method to get a single clipper based on id
    @Override
    public Clipper getClipperWithId(String id) throws IncorrectInputException {
        var clipperWithId = clipperData.getClipperWithId(UUID.fromString(id));
        if (clipperWithId == null) {
            log.error("Clipper with id {} not found", id);
            throw new IncorrectInputException(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + id);
        }
        log.info("Clipper with id {} found", id);
        return clipperWithId;
    }

    @Override
    public List<Clipper> getAllClippers() {
        log.info("Getting all clippers");
        return clipperData.getAllClippers();
    }

    //method to update clipper with new values based on id
    @Override
    public Clipper updateClipper(Clipper clipperWithUpdate) throws IncorrectInputException, DatabaseFailedOperationException {
        //make sure clipper with supplied id exists.
        getClipperWithId(clipperWithUpdate.getId().toString());
        var updatedClipper = clipperData.updateClipper(clipperWithUpdate);
        if (updatedClipper == null) {
            log.error("Clipper could not be updated");
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_GET_FAILED);
        }
        log.info("Clipper with id {} updated", clipperWithUpdate.getId());
        return updatedClipper;
    }

    //method to remove clipper based on id
    @Override
    public void deleteClipper(String clipperId) throws IncorrectInputException, DatabaseFailedOperationException {
        //ensure clipper with id exists
        getClipperWithId(clipperId);

        //todo let JPA remove collected clippers on delete???
        var collectedClippers = collectedClipperService.getCollectedClippersForClipperId(clipperId);
        log.info("Removing {} collected clippers for clipper {}", collectedClippers.size(), clipperId);
        if(collectedClippers.size() != 0){
            for(CollectedClipper cc: collectedClippers){
                collectedClipperService.deleteCollectedClipper(cc.getId().toString());
            }
        }
        if (clipperData.deleteClipper(UUID.fromString(clipperId)) != null) {
            log.info("Clipper with id {} removed", clipperId);
            throw new DatabaseFailedOperationException(ExceptionMessages.CLIPPER_PRESENT_AFTER_DELETE);
        }
    }
}

package noccures.clipperms.service;

import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.interfaces.ISeriesDataSource;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SeriesService implements ISeriesService {

    ISeriesDataSource seriesData;

    @Autowired
    public SeriesService(ISeriesDataSource seriesDataSource) {
        this.seriesData = seriesDataSource;
    }

    //Method to add new series.
    @Override
    public Series addSeries(Series seriesToAdd) throws IncorrectInputException, DatabaseFailedOperationException {
        //check if name is not empty
        if (seriesToAdd.getName().isBlank()) {
            log.error("Series name is empty");
            throw new IncorrectInputException(ExceptionMessages.SERIES_NO_NAME);
        }
        if(seriesToAdd.getId() == null){
            seriesToAdd.setId(UUID.randomUUID());
        }
        var expectedResult = seriesData.addSeries(seriesToAdd);
        if (expectedResult == null) {
            log.error("Series could not be saved to database");
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_GET_FAILED);
        }
        log.info("Series with id {} was successfully saved to database", expectedResult.getId());
        return expectedResult;
    }

    //Get the available series number -> needed to update ui and prevent duplicate entrees
    @Override
    public List<Integer> getAvailableSeriesNumber(String id) throws DatabaseFailedOperationException {
        var seriesWithId = getSeriesWithId(id);
        //Custom series -> clippers get numbers according to add order
        if (seriesWithId.isCustom()) {
            return List.of(seriesWithId.getClippers().size() + 1);
        }
        //actual clipper series -> check what numbers have been added
        var takenSeriesNumbers = seriesData.getTakenSeriesNumber(UUID.fromString(id));
        List<Integer> possibleNumbers = new ArrayList<>(Arrays.asList(1,2,3,4));
        for(int i: takenSeriesNumbers){
            possibleNumbers.remove(Integer.valueOf(i));
        }
        log.info("Available series number for series with id {} is {}", id, possibleNumbers);
        return possibleNumbers;
    }

    //get series with specific id
    @Override
    public Series getSeriesWithId(String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesData.getSeriesWithId(UUID.fromString(id));
        if (seriesWithId == null) {
            log.error("Series with id {} could not be found", id);
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + id);
        }
        log.info("Series with id {} was successfully found", id);
        return seriesWithId;
    }

    @Override
    public List<Series> getAllSeries() {
        return seriesData.getAllSeries();
    }

    @Override
    public Series updateSeries(Series seriesWithUpdate) throws DatabaseFailedOperationException {
        //make sure series with supplied id exists.
        getSeriesWithId(seriesWithUpdate.getId().toString());
        var updatedSeries = seriesData.updateSeries(seriesWithUpdate);
        if (updatedSeries == null) {
            log.error("Series with id {} could not be updated", seriesWithUpdate.getId());
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_GET_FAILED);
        }
        log.info("Series with id {} was successfully updated", seriesWithUpdate.getId());
        return updatedSeries;
    }

    //delete series based on id
    @Override
    public void deleteSeries(String id) throws DatabaseFailedOperationException {
        //check if series to delete exists
        getSeriesWithId(id);

        //return should be null after delete
        if (seriesData.deleteSeries(UUID.fromString(id)) != null) {
            log.error("Series with id {} could not be deleted", id);
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_PRESENT_AFTER_DELETE);
        }
        log.info("Series with id {} was successfully deleted", id);
    }
}

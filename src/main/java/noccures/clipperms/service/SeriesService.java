package noccures.clipperms.service;

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
            throw new IncorrectInputException(ExceptionMessages.SERIES_NO_NAME);
        }
        //assign new random id to series
        seriesToAdd.setId(UUID.randomUUID().toString());
        var expectedResult = seriesData.addSeries(seriesToAdd);
        if (expectedResult == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_GET_FAILED);
        }
        return expectedResult;
    }

    //Get the available series number -> needed to update ui and prevent duplicate entrees
    @Override
    public int getAvailableSeriesNumber(String id) throws DatabaseFailedOperationException {
        var seriesWithId = getSeriesWithId(id);
        //Custom series -> clippers get numbers according to add order
        if (seriesWithId.isCustom()) {
            return seriesWithId.getClippers().size() + 1;
        }
        //actual clipper series -> check what numbers have been added
        var takenSeriesNumbers = seriesData.getTakenSeriesNumber(id);
        List<Integer> possibleNumbers = new ArrayList<>(Arrays.asList(1,2,3,4));
        for(int i: takenSeriesNumbers){
            possibleNumbers.remove(Integer.valueOf(i));
        }
        return possibleNumbers.get(0);
    }

    //get series with specific id
    @Override
    public Series getSeriesWithId(String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesData.getSeriesWithId(id);
        if (seriesWithId == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + id);
        }
        return seriesWithId;
    }

    @Override
    public List<Series> getAllSeries() {
        return seriesData.getAllSeries();
    }

    @Override
    public Series updateSeries(Series seriesWithUpdate) throws DatabaseFailedOperationException {
        //make sure series with supplied id exists.
        getSeriesWithId(seriesWithUpdate.getId());
        var updatedSeries = seriesData.updateSeries(seriesWithUpdate);
        if (updatedSeries == null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_GET_FAILED);
        }
        return updatedSeries;
    }

    //delete series based on id
    @Override
    public void deleteSeries(String id) throws DatabaseFailedOperationException {
        //check if series to delete exists
        getSeriesWithId(id);

        //return should be null after delete
        if (seriesData.deleteSeries(id) != null) {
            throw new DatabaseFailedOperationException(ExceptionMessages.SERIES_PRESENT_AFTER_DELETE);
        }
    }
}

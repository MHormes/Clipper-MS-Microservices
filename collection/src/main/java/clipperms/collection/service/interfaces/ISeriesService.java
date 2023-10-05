package clipperms.collection.service.interfaces;

import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.Series;

import java.util.List;

public interface ISeriesService {

    Series addSeries(Series seriesToAdd) throws IncorrectInputException, DatabaseFailedOperationException;

    List<Integer> getAvailableSeriesNumber(String id) throws DatabaseFailedOperationException;

    Series getSeriesWithId(String id) throws DatabaseFailedOperationException;

    List<Series> getAllSeries();

    Series updateSeries(Series seriesWithUpdate) throws DatabaseFailedOperationException;

    void deleteSeries(String id) throws DatabaseFailedOperationException;
}

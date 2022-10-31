package noccures.clipperms.service.interfaces;

import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Series;

import java.util.List;

public interface ISeriesService {

    Series addSeries(Series seriesToAdd) throws IncorrectInputException, DatabaseFailedOperationException;

    int getAvailableSeriesNumber(String id) throws DatabaseFailedOperationException;

    Series getSeriesWithId(String id) throws DatabaseFailedOperationException;

    List<Series> getAllSeries();

    Series updateSeries(Series seriesWithUpdate) throws DatabaseFailedOperationException;

    void deleteSeries(String id) throws DatabaseFailedOperationException;
}

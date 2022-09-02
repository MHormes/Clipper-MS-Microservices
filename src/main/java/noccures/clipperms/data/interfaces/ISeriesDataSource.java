package noccures.clipperms.data.interfaces;

import noccures.clipperms.model.Series;

import java.util.List;

public interface ISeriesDataSource {

    Series addSeries(Series seriesToAdd);

    int[] getTakenSeriesNumber(String id);

    Series getSeriesWithId(String id);

    List<Series> getAllSeries();

    Series updateSeries(Series seriesWithUpdate);

    Series deleteSeries(String id);
}

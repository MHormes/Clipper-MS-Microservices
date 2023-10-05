package clipperms.collection.data.interfaces;

import clipperms.collection.model.Series;

import java.util.List;
import java.util.UUID;

public interface ISeriesDataSource {

    Series addSeries(Series seriesToAdd);

    int[] getTakenSeriesNumber(UUID id);

    Series getSeriesWithId(UUID id);

    List<Series> getAllSeries();

    Series updateSeries(Series seriesWithUpdate);

    Series deleteSeries(UUID id);
}

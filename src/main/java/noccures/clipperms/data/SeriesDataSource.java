package noccures.clipperms.data;

import noccures.clipperms.data.interfaces.ISeriesDataSource;
import noccures.clipperms.data.repositories.ISeriesRepository;
import noccures.clipperms.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeriesDataSource implements ISeriesDataSource {

    ISeriesRepository seriesRepo;

    @Autowired
    public SeriesDataSource(ISeriesRepository seriesRepository){
        this.seriesRepo = seriesRepository;
    }

    @Override
    public Series addSeries(Series seriesToAdd) {
        seriesRepo.save(seriesToAdd);
        return getSeriesWithId(seriesToAdd.getId());
    }

    @Override
    public int[] getTakenSeriesNumber(String id) {
        return seriesRepo.getAvailableSeriesNumbers(id);
    }

    @Override
    public Series getSeriesWithId(String id) {
        Optional<Series> series = seriesRepo.findById(id);
        return series.orElse(null);
    }

    @Override
    public List<Series> getAllSeries() {
        return seriesRepo.findAll();
    }

    @Override
    public Series updateSeries(Series seriesWithUpdate) {
        //Get the series to update
        var seriesToUpdate = seriesRepo.getSeriesById(seriesWithUpdate.getId());
        //Assign new values
        seriesToUpdate.setName(seriesWithUpdate.getName());
        seriesToUpdate.setClippers(seriesWithUpdate.getClippers());
        seriesToUpdate.setCustom(seriesWithUpdate.isCustom());
        seriesToUpdate.setComplete(seriesWithUpdate.isComplete());

        //save new values
        seriesRepo.save(seriesToUpdate);

        //get series from db after update, using incoming data to verify the update was successful
        return getSeriesWithId(seriesWithUpdate.getId());
    }

    @Override
    public Series deleteSeries(String id) {
        seriesRepo.deleteById(id);
        return getSeriesWithId(id);
    }
}

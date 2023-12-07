package clipperms.collection.data;

import clipperms.collection.data.interfaces.ISeriesDataSource;
import clipperms.collection.data.repositories.ISeriesRepository;
import clipperms.collection.model.Series;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public int[] getTakenSeriesNumber(UUID id) {
        return seriesRepo.getAvailableSeriesNumbers(id);
    }

    @Override
    public Series getSeriesWithId(UUID id) {
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
        var seriesToUpdate = seriesRepo.findById(seriesWithUpdate.getId()).orElseThrow(() -> new EntityNotFoundException("Series with id " + seriesWithUpdate.getId() + " not found"));
        //Assign new values
        seriesToUpdate.setName(seriesWithUpdate.getName());
        seriesToUpdate.setClippers(seriesWithUpdate.getClippers());
        seriesToUpdate.setCustom(seriesWithUpdate.isCustom());

        //save new values
        seriesRepo.save(seriesToUpdate);

        //get series from db after update, using incoming data to verify the update was successful
        return getSeriesWithId(seriesWithUpdate.getId());
    }

    @Override
    public Series deleteSeries(UUID id) {
        seriesRepo.deleteById(id);
        return getSeriesWithId(id);
    }
}

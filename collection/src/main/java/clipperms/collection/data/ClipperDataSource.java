package clipperms.collection.data;

import clipperms.collection.data.interfaces.IClipperDataSource;
import clipperms.collection.data.repositories.IClipperRepository;
import clipperms.collection.data.repositories.ISeriesRepository;
import clipperms.collection.model.Clipper;
import clipperms.collection.model.Series;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClipperDataSource implements IClipperDataSource {

    IClipperRepository clipperRepo;

    ISeriesRepository seriesRepo;

    @Autowired
    public ClipperDataSource(IClipperRepository clipperRepository, ISeriesRepository seriesRepository) {
        clipperRepo = clipperRepository;
        seriesRepo = seriesRepository;
    }

    @Override
    public Clipper saveClipper(Clipper clipperToAdd) {
        clipperRepo.save(clipperToAdd);
        return getClipperWithId(clipperToAdd.getId());
    }

    @Override
    public Series getExistingSeriesForNewClipper(UUID seriesId) {
        return seriesRepo.findById(seriesId).orElseThrow(() -> new EntityNotFoundException("Series with id " + seriesId + " not found"));
    }

    @Override
    public Clipper getClipperWithId(UUID id) {
        Optional<Clipper> clipperWithId = clipperRepo.findById(id);
        return clipperWithId.orElse(null);
    }

    @Override
    public List<Clipper> getAllClippers() {
        return clipperRepo.findAll();
    }

    @Override
    public Clipper updateClipper(Clipper clipperWithUpdate) {
        //find reference for clipper to update
        var clipperToUpdate = clipperRepo.findById(clipperWithUpdate.getId()).orElseThrow(() -> new EntityNotFoundException("Clipper with id " + clipperWithUpdate.getId() + " not found"));
        //assign values from incoming clipper to clipper in db
        clipperToUpdate.setName(clipperWithUpdate.getName());
        clipperToUpdate.setSeries(clipperWithUpdate.getSeries());
        clipperToUpdate.setSeriesNumber(clipperWithUpdate.getSeriesNumber());

        //image data is null if picture is the same -> no need to update, means no need to send over the web
        if (clipperWithUpdate.getImageData() != null) {
            clipperToUpdate.setImageData(clipperWithUpdate.getImageData());
        }
        clipperToUpdate.setCreatedBy(clipperWithUpdate.getCreatedBy());
        //save changes made to clipper
        clipperRepo.save(clipperToUpdate);

        //get clipper from db after update, using incoming data to verify the update was successful
        return getClipperWithId(clipperWithUpdate.getId());
    }

    @Override
    public Clipper deleteClipper(UUID clipperId) {
        clipperRepo.deleteById(clipperId);
        return getClipperWithId(clipperId);
    }

    @Override
    public void deleteAllClippersInSeries(UUID seriesId) {
        clipperRepo.deleteAllBySeriesId(seriesId);
    }
}

package noccures.clipperms.data;

import noccures.clipperms.data.interfaces.IClipperDataSource;
import noccures.clipperms.data.repositories.IClipperRepository;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClipperDataSource implements IClipperDataSource {

    IClipperRepository clipperRepo;

    @Autowired
    public ClipperDataSource(IClipperRepository clipperRepository) {
        clipperRepo = clipperRepository;
    }

    @Override
    public Clipper saveClipper(Clipper clipperToAdd) {
        clipperRepo.save(clipperToAdd);
        return getClipperWithId(clipperToAdd.getId());
    }

    @Override
    public Series getExistingSeriesForNewClipper(String seriesId) {
        return clipperRepo.findSeriesBySeriesId(seriesId);
    }

    @Override
    public Clipper getClipperWithId(String id) {
        Optional<Clipper> clipperWithId = clipperRepo.findById(id);
        return clipperWithId.orElse(null);
    }

    @Override
    public Clipper updateClipper(Clipper clipperWithUpdate) {
        //find reference for clipper to update
        var clipperToUpdate = clipperRepo.getClipperById(clipperWithUpdate.getId());
        //assign values from incoming clipper to clipper in db
        clipperToUpdate.setName(clipperWithUpdate.getName());
        clipperToUpdate.setSeriesId(clipperWithUpdate.getSeriesId());
        clipperToUpdate.setSeriesNumber(clipperWithUpdate.getSeriesNumber());
        clipperToUpdate.setNotes(clipperWithUpdate.getNotes());
        //save changes made to clipper
        clipperRepo.save(clipperToUpdate);

        //get clipper from db after update, using incoming data to verify the update was successful
        return getClipperWithId(clipperWithUpdate.getId());
    }

    @Override
    public Clipper deleteClipper(String clipperId) {
        clipperRepo.deleteById(clipperId);
        return getClipperWithId(clipperId);
    }
}

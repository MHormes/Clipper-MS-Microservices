package noccures.clipperms.data.repositories;

import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClipperRepository extends JpaRepository<Clipper, UUID> {
    Clipper getClipperById(UUID id);

    @Query("SELECT s FROM Series s where s.id = :seriesId")
    Series findSeriesBySeriesId(UUID seriesId);

}

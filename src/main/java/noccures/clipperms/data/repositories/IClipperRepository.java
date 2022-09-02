package noccures.clipperms.data.repositories;

import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClipperRepository extends JpaRepository<Clipper, String> {
    Clipper getClipperById(String id);

    @Query("SELECT s FROM Series s where s.id = :seriesId")
    Series findSeriesBySeriesId(String seriesId);

}

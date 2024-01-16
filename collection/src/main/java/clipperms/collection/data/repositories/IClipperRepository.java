package clipperms.collection.data.repositories;

import clipperms.collection.model.Clipper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClipperRepository extends JpaRepository<Clipper, UUID> {
    Clipper getClipperById(UUID id);

    void deleteClipperById(UUID id);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM Clipper c
            WHERE c.series.id = :seriesId
            """)
    void deleteAllBySeriesId(@Param("seriesId") UUID seriesId);
}

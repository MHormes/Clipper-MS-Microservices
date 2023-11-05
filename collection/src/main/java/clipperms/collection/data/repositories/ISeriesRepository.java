package clipperms.collection.data.repositories;

import clipperms.collection.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISeriesRepository extends JpaRepository<Series, UUID> {

    Series getSeriesById(UUID id);

    @Query("SELECT c.seriesNumber from Series s inner join Clipper c on s.id = c.series.id where s.id = :id")
    int[] getAvailableSeriesNumbers(UUID id);
}

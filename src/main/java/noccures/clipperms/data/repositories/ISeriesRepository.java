package noccures.clipperms.data.repositories;

import noccures.clipperms.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ISeriesRepository extends JpaRepository<Series, String> {

    Series getSeriesById(String id);

    @Query("SELECT c.seriesNumber from Series s inner join Clipper c on s.id = c.seriesId where s.id = :id")
    int[] getAvailableSeriesNumbers(String id);
}

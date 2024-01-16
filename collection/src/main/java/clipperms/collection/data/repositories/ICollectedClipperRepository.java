package clipperms.collection.data.repositories;

import clipperms.collection.model.CollectedClipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICollectedClipperRepository extends JpaRepository<CollectedClipper, UUID> {

    CollectedClipper getCollectedClipperById(UUID id);

    List<CollectedClipper> getCollectedClippersByClipperIdId(UUID clipperId);

    @Modifying
    @Query("delete from CollectedClipper c where c.clipperId = ?1 and c.userId = ?2")
    void deleteCollectedClipperByClipperIdAndUserId(UUID clipperId, UUID userId);
}

package clipperms.collection.data.repositories;

import clipperms.collection.model.CollectedClipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICollectedClipperRepository extends JpaRepository<CollectedClipper, UUID> {

    CollectedClipper getCollectedClipperById(UUID id);

    List<CollectedClipper> getCollectedClippersByClipperIdId(UUID clipperId);
}

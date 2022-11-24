package noccures.clipperms.data.repositories;

import noccures.clipperms.model.CollectedClipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICollectedClipperRepository extends JpaRepository<CollectedClipper, UUID> {

    CollectedClipper getCollectedClipperById(UUID id);

    List<CollectedClipper> getCollectedClippersByClipperId_Id(UUID clipperId);
}

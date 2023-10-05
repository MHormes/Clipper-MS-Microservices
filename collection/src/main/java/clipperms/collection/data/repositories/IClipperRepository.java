package clipperms.collection.data.repositories;

import clipperms.collection.model.Clipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClipperRepository extends JpaRepository<Clipper, UUID> {
    Clipper getClipperById(UUID id);



}

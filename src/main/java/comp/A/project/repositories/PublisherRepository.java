package comp.A.project.repositories;

import comp.A.project.DAO.PublisherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PublisherRepository extends CrudRepository<PublisherEntity, String> {

    Optional<PublisherEntity> findByName(String name);
}

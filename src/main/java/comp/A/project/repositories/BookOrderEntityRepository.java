package comp.A.project.repositories;

import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.DAO.BookOrderEntityId;
import org.springframework.data.repository.CrudRepository;

public interface BookOrderEntityRepository extends CrudRepository<BookOrderEntity, BookOrderEntityId> {
}

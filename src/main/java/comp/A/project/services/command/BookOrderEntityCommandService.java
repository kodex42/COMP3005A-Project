package comp.A.project.services.command;

import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.repositories.BookOrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderEntityCommandService {
    @Autowired
    private BookOrderEntityRepository bookOrderEntityRepository;

    public BookOrderEntity create(BookOrderEntity bookOrderEntity) {
        return bookOrderEntityRepository.save(bookOrderEntity);
    }

    public void remove(BookOrderEntity bookOrderEntity) {
        bookOrderEntityRepository.delete(bookOrderEntity);
    }
}

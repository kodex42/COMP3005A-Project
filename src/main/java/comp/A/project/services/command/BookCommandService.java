package comp.A.project.services.command;

import comp.A.project.DAO.BookEntity;
import comp.A.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCommandService {
    @Autowired
    private BookRepository bookRepository;

    public BookEntity save(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }
}

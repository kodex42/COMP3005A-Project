package comp.A.project.services.query;

import comp.A.project.DAO.BookOrderEntity;
import comp.A.project.DAO.OrderEntity;
import comp.A.project.repositories.BookOrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderEntityQueryService {
    @Autowired
    private BookOrderEntityRepository bookOrderEntityRepository;

    public double getTotalIncome() {
        Iterable<BookOrderEntity> bookOrderEntities = bookOrderEntityRepository.findAll();
        double income = 0.0;
        for (BookOrderEntity b : bookOrderEntities) {
            income += b.getQuantity() * b.getBook().getPrice();
        }
        return income;
    }

    public double getTotalPublisherCut() {
        Iterable<BookOrderEntity> bookOrderEntities = bookOrderEntityRepository.findAll();
        double cut = 0.0;
        for (BookOrderEntity b : bookOrderEntities) {
            cut += b.getQuantity() * b.getBook().getPrice() * b.getBook().getPublisherPercentage();
        }
        return cut;
    }

    public Iterable<BookOrderEntity> getAllWithIsbn(String isbn) {
        return bookOrderEntityRepository.getAllWithIsbn(isbn);
    }
}

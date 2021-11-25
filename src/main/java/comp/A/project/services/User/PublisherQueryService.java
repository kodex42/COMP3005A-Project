package comp.A.project.services.User;

import comp.A.project.DAO.PublisherEntity;
import comp.A.project.repositories.PublisherRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherQueryService {
    @Autowired
    private PublisherRepository publisherRepository;

    public Iterable<PublisherEntity> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public PublisherEntity getByName(String name) throws NotFoundException {
        return this.publisherRepository.findByName(name).orElseThrow(() -> new NotFoundException("Publisher with specified name not found"));
    }
}

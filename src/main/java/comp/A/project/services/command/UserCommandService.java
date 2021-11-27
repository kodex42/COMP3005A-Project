package comp.A.project.services.command;

import comp.A.project.DAO.UserEntity;
import comp.A.project.forms.UserForm;
import comp.A.project.repositories.UserRepository;
import comp.A.project.services.query.UserQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserCommandService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryService userQueryService;

    public UserEntity createUser(UserForm userForm) throws EntityExistsException {

        if (userRepository.existsByUsername(userForm.getUsername()))
            throw new EntityExistsException("A user with the username '" + userForm.getUsername() + "' already exists");

        return userRepository.save(new UserEntity(userForm));
    }

    public void removeUserByUsername(String username) {
        try {
            userRepository.delete(userQueryService.getByUsername(username));
        } catch (IllegalArgumentException | NotFoundException e) {
            System.out.println("Tried to delete non-existent user " + username);
        }
    }
}

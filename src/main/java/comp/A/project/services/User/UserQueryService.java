package comp.A.project.services.User;

import comp.A.project.DAO.UserEntity;
import comp.A.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

@Service
public class UserQueryService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        try {
            userEntity = getByUsername(username);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("User not found");
        }

        return userEntity;
    }

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getByUsername(String username) throws NotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with specified username not found"));
    }
}

package comp.A.project.services.query;

import comp.A.project.DAO.AddressEntity;
import comp.A.project.repositories.AddressRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressQueryService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressEntity findMatchingAddress(String postal_code, String street_address, String province, String town) throws NotFoundException {
        return addressRepository.findMatchingAddress(postal_code, street_address, province, town).orElseThrow(() -> new NotFoundException("Address with given attributes does not exist"));
    }
}

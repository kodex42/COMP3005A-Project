package comp.A.project.services.command;

import comp.A.project.DAO.AddressEntity;
import comp.A.project.forms.AddressForm;
import comp.A.project.repositories.AddressRepository;
import comp.A.project.services.query.AddressQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressCommandService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressQueryService addressQueryService;

    public AddressEntity create(AddressForm addressForm) {
        AddressEntity addressEntity;
        try {
            addressEntity = addressQueryService.findMatchingAddress(addressForm.getPostalCode(), addressForm.getStreetAddress(), addressForm.getProvince(), addressForm.getTown());
            addressEntity.setName(addressForm.getName());
        } catch (NotFoundException e) {
            addressEntity = new AddressEntity(addressForm);
        }
        return save(addressEntity);
    }

    public AddressEntity save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }
}

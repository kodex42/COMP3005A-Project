package comp.A.project.repositories;

import comp.A.project.DAO.AddressEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    @Query(value = "SELECT a.address_id, a.\"name\", a.postal_code, a.street_address FROM address a NATURAL JOIN postal_region p WHERE a.postal_code=?1 AND a.street_address=?2 AND p.province=?3 AND p.town=?4 LIMIT 1", nativeQuery = true)
    Optional<AddressEntity> findMatchingAddress(String postal_code, String street_address, String province, String town);
}

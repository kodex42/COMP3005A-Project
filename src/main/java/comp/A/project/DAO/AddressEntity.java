package comp.A.project.DAO;

import comp.A.project.forms.AddressForm;
import comp.A.project.services.query.AddressQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "public")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long addressId;
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "postal_code", nullable = false)
    private PostalRegionEntity postalRegion;
    private String streetAddress;

    public AddressEntity() {
        super();
    }

    public AddressEntity(AddressForm addressForm) {
        super();
        this.name = addressForm.getName();
        this.postalRegion = new PostalRegionEntity(addressForm.getPostalCode(), addressForm.getProvince(), addressForm.getTown());
        this.streetAddress = addressForm.getStreetAddress();
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostalRegionEntity getPostalRegion() {
        return postalRegion;
    }

    public void setPostalRegion(PostalRegionEntity postalRegion) {
        this.postalRegion = postalRegion;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return name + ": " +
                postalRegion.getPostalCode() + ", " +
                postalRegion.getProvince() + ", " +
                postalRegion.getTown() + ", " +
                streetAddress;
    }
}

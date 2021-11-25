package comp.A.project.DAO;

import comp.A.project.forms.AddressForm;
import comp.A.project.forms.UserForm;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "public")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long addressId;
    private String name;
    @ManyToOne
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
}

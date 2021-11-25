package comp.A.project.DAO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "postal_region", schema = "public")
public class PostalRegionEntity {
    @Id
    private String postalCode;
    private String province;
    private String town;

    public PostalRegionEntity() {
        super();
    }

    public PostalRegionEntity (String postalCode, String province, String town) {
        super();
        this.postalCode = postalCode;
        this.province = province;
        this.town = town;
    }
}

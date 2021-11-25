package comp.A.project.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AddressForm {
    private String name;
    @Pattern(regexp = "^[A-Z]\\d[A-Z] \\d[A-Z]\\d", message = "Post code is not valid")
    @NotEmpty(message = "Postal code is required")
    private String postalCode;
    @NotEmpty(message = "Province is required")
    private String province;
    @NotEmpty(message = "Town is required")
    private String town;
    @NotEmpty(message = "Street address is required")
    private String streetAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
}

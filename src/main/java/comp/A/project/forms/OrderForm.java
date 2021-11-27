package comp.A.project.forms;

import comp.A.project.DAO.UserEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

public class OrderForm {
    @NotNull(message = "User cannot be null")
    private UserEntity user;
    @Valid
    private AddressForm billingAddress;
    @NotNull
    private Boolean saveBilling = false;
    @Valid
    private AddressForm shippingAddress;
    @NotNull
    private Boolean saveShipping = false;
    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price cannot be negative")
    private Double total;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getLocation() {
        return "Warehouse A, Ottawa, Ontario";
    }

    public String getStatus() {
        return "Processing";
    }

    public Timestamp getDate() {
        return Timestamp.from(Instant.now());
    }

    public AddressForm getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressForm billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressForm getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressForm shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isSaveBilling() {
        return saveBilling;
    }

    public void setSaveBilling(boolean saveBilling) {
        this.saveBilling = saveBilling;
    }

    public boolean isSaveShipping() {
        return saveShipping;
    }

    public void setSaveShipping(boolean saveShipping) {
        this.saveShipping = saveShipping;
    }
}

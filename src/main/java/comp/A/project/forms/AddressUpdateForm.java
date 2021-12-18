package comp.A.project.forms;

import comp.A.project.DAO.UserEntity;

import javax.validation.Valid;

public class AddressUpdateForm {
    @Valid
    AddressForm billingAddress;
    @Valid
    AddressForm shippingAddress;

    public AddressUpdateForm() {
        super();
        this.billingAddress = new AddressForm();
        this.shippingAddress = new AddressForm();
    }

    public AddressUpdateForm(UserEntity user) {
        this.billingAddress = new AddressForm(user.getBillingAddress());
        this.shippingAddress = new AddressForm(user.getShippingAddress());
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
}

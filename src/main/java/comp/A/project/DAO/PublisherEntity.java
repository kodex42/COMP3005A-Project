package comp.A.project.DAO;

import javax.persistence.*;

@Entity
@Table(name = "publisher", schema = "public")
public class PublisherEntity {
    @Id
    private String name;
    @ManyToOne
    @JoinColumn(name = "address", nullable = true)
    private AddressEntity address;
    private String email;
    private String phone;
    private String bankAccountNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
}

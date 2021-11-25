package comp.A.project.DAO;

import comp.A.project.forms.UserForm;
import comp.A.project.security.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements UserDetails {
    @Id
    private String username;
    private String password;
    private String userType;
    @ManyToOne
    @JoinColumn(name = "billing_address", nullable = true)
    private AddressEntity billingAddress;
    @ManyToOne
    @JoinColumn(name = "shipping_address", nullable = true)
    private AddressEntity shippingAddress;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<OrderEntity> orders;

    public UserEntity() {
        super();
    }

    public UserEntity(UserForm userForm) {
        super();
        this.username = userForm.getUsername();
        this.password = userForm.getPassword();
        this.userType = "USER";
        this.billingAddress = null;
        this.shippingAddress = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserType type = new UserType();
        type.setAuth(this.getAuthority());
        return Collections.singleton(type);
    }

    public String getAuthority() {
        return this.userType;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AddressEntity getBillingAddress() {
        return billingAddress;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }
}

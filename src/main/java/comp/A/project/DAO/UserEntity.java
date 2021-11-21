package comp.A.project.DAO;

import comp.A.project.forms.UserForm;
import comp.A.project.security.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements UserDetails {
    @Id
    private String username;
    private String password;
    private String userType;
    private Integer billingAddress; // TO BE CHANGED TO DAO LATER
    private Integer shippingAddress; // TO BE CHANGED TO DAO LATER

    public UserEntity() {
        super();
    }

    public UserEntity(UserForm userForm) {
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

    public Integer getBillingAddress() {
        return billingAddress;
    }

    public Integer getShippingAddress() {
        return shippingAddress;
    }
}

package comp.A.project.security;

import org.springframework.security.core.GrantedAuthority;

public class UserType implements GrantedAuthority {
    private String auth = "USER";

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String getAuthority() {
        return this.getAuth();
    }
}

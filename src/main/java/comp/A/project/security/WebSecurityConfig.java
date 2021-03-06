package comp.A.project.security;

import comp.A.project.services.query.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserQueryService userQueryService;

    // For the sake of simplicity and education, this web application does not encrypt passwords
    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/user/create").permitAll()
                    .antMatchers( "/img/*").permitAll()
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    .antMatchers("/finances").hasAuthority("ADMIN")
                    .antMatchers("/book/create").hasAuthority("ADMIN")
                    .antMatchers("/book/remove").hasAuthority("ADMIN")
                    .antMatchers("/book/new").hasAuthority("ADMIN")
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/", true)
                    .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                    .and()
                .httpBasic();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userQueryService).passwordEncoder(passwordEncoder);
    }
}

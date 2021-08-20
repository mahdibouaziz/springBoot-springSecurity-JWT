package jwt.spring.usersecurity.security;

import jwt.spring.usersecurity.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        NOTES
        - WebSecurityConfigurerAdapter is the main security class
            we extend this class to tell spring how we want to manage the users and the security in the application
    */

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        this method is gonna tell spring how to look for users
    */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
            the userDetailsService accept a userDetail service as a parameter which is a bean
            that we have to override and tell spring how to look for the users
            it also needs a passwordEncoder that accept a BCryptPasswordEncoder

            NOTE: we need to create those 2 beans (userDetailsService and bCryptPasswordEncoder) in our application
            to be able to use them (we're gonna create them in the main class)
        */
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /*
        this method is responsible to configure the method for auth
        we're going to use JWT

     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // disable the csrf
        http.sessionManagement().sessionCreationPolicy(STATELESS); //
        //http.authorizeRequests().anyRequest().permitAll();// allow everyone to be able to access this application
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}

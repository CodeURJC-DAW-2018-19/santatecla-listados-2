package com.example.demo.security;

import com.example.demo.security.UserRepositoryAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityController extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthProvider userRepoAuthProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**");

        // URLs that need authentication to access to it
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/logIn").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/register/newUser");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/all").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/{userName}").hasAnyRole("TEACHER");



        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/student/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/student/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/teacher/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/student/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/teacher/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/student/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/teacher/**").hasAnyRole("TEACHER");

        // Other URLs can be accessed without authentication
        http.authorizeRequests().anyRequest().permitAll();

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf().disable();

        // Use Http Basic Authentication
        http.httpBasic();

        // Do not redirect when logout
        http.logout().logoutSuccessHandler((rq, rs, a) -> {	});
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Database authentication provider
        auth.authenticationProvider(userRepoAuthProvider);
    }
}
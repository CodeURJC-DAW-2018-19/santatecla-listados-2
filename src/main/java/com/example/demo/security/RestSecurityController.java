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

        //topic URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topic/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/topic/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/topic/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/topic/**").hasAnyRole("TEACHER");

        //concept URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/concept/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/concept/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/concept/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/concept/**").hasAnyRole("TEACHER");

        //item URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/item/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/item/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/item/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/item/**").hasAnyRole("TEACHER");

        //question URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/question/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/question/**").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/question/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/question/**").hasAnyRole("TEACHER");

        //answer URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/answer/**").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/answer/**").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/answer/**").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/answer/**").hasAnyRole("STUDENT");


        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/search/").permitAll();


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
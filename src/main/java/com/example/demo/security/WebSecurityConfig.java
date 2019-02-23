package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthProvider userRepoAuthProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// Public pages
        http.authorizeRequests().antMatchers("/logOut").permitAll();
        http.authorizeRequests().antMatchers("/logIn").permitAll();
        http.authorizeRequests().antMatchers("/MainPage/Guest").permitAll();
        http.authorizeRequests().antMatchers("/MainPage/search").permitAll();
        http.authorizeRequests().antMatchers("/TopicMoreButton").permitAll();
        http.authorizeRequests().antMatchers("/image").permitAll();
        http.authorizeRequests().antMatchers("/image/Guest").permitAll();



        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/newImage").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers("/image/upload").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers("/MainPage").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers("/MainPage/Teacher/**").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers("/MainPage/Student/**").hasAnyRole("STUDENT");

        // Login form
        http.formLogin().loginPage("/logIn");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/MainPage");
        http.formLogin().failureUrl("/loginError");

        //LogOut form
        http.logout().logoutUrl("/logOut");
        http.logout().logoutSuccessUrl("/logIn");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Database authentication provider
        auth.authenticationProvider(userRepoAuthProvider);
    }
}

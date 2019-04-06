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
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/logIn").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/logOut").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/register").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/{userName}").hasAnyRole("TEACHER");

        //diagram URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/diagramInfo").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/conceptDiagramInfo/{id}").permitAll();

        //topic URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics/size").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics/").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics/{id}").hasAnyRole("TEACHER", "STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/topics/").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/topics/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/topics/{id}").hasAnyRole("TEACHER");

        //concept URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/concepts/").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/concepts/{id}").hasAnyRole("TEACHER", "STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/concepts/").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/concepts/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/concepts/{id}").hasAnyRole("TEACHER");

        //item URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/items/").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/items/{id}").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/items/").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/items/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/items/{id}").hasAnyRole("TEACHER");

        //question URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/questions/").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/questions/{id}").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/questions/{id}").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/questions/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/questions/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/questions/concept/{id}/{corrected}").hasAnyRole("STUDENT","TEACHER");

        //answer URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/answers/").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/answers/{id}").hasAnyRole("STUDENT","TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/answers/").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/answers/{id}").hasAnyRole("STUDENT");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/answers/question{questionId}/{id}").hasAnyRole("STUDENT");

        //image URLs
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/images/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/images/newImage/{title}/{concept}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/images/{id}").hasAnyRole("TEACHER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/images/{id}/updateImage/{name}").hasAnyRole("TEACHER");


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

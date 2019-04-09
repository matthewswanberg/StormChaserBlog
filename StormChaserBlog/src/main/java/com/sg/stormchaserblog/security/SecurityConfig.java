/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author RAC
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    public void configureGlobalInMemory(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("{noop}password").roles("ADMIN")
                .and()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("neither").password("{noop}password").roles("POTATO");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/content-manager").hasRole("ADMIN")
                .antMatchers("/createblog").hasAnyRole("USER", "ADMIN")
                .antMatchers("/", "home", "/about-us", "/posts", "/post/**").permitAll()
                .antMatchers("/JS/JS.js").permitAll()
                .antMatchers("/CSS/CSS.css").permitAll()
                .antMatchers("/IMG/**").permitAll()

//                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?login_error=1")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/home")
                    .permitAll();
    }

}

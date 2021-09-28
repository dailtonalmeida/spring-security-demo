/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.dsa.springbootsoujava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 *
 * @author dailtonalmeida
 */
@EnableWebSecurity
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("ronaldinho").password("gaucho").roles("USER")
                    .and()
                    .withUser("lionel").password("messi").roles("USER", "ADMIN")
                    .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                ;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/todo/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                // some more method calls
                .formLogin()
                .and()
                .logout()
                ;
    }
}

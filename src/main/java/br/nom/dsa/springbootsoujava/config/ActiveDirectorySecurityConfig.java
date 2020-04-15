/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.dsa.springbootsoujava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

/**
 *
 * @author dailtonalmeida
 */
//@EnableWebSecurity
@ConfigurationProperties
public class ActiveDirectorySecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${custom.ldap.domain}")
    private String ldapDomain;
    @Value("${custom.ldap.url}")
    private String ldapUrl;
    @Value("${custom.ldap.rootDn}")
    private String ldapRootDn;

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
//        return new PersonContextMapper();
        return new CustomUserDetailsContextMapper();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider result = new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl, ldapRootDn);
        result.setConvertSubErrorCodesToExceptions(true);
        result.setUseAuthenticationRequestCredentials(true);
        result.setUserDetailsContextMapper(userDetailsContextMapper());

        return result;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider())
                ;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/todo/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN_USER")
                .and()
                // some more method calls
                .formLogin()
                .and()
                .logout()
                ;
    }
}

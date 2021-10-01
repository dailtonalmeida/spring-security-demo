/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.dsa.springsecuritygft.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author dnse
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication auth0) throws AuthenticationException {
        FileInputStream fis = null;
        String principal = (String) auth0.getPrincipal();
        Object credentials = auth0.getCredentials();
        try {
            File usersFile = new File(System.getProperty("user.home"), "baseOfUsers.properties");
            fis = new FileInputStream(usersFile);
            Properties props = new Properties();
            props.load(fis);
            fis.close();
            if (!Objects.equals(credentials, props.get(principal))) {
                throw new RuntimeException("BAD CREDENTIALS!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadCredentialsException(ex.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            }
        }

        String role = "lele".equalsIgnoreCase(principal) ? "ROLE_ADMIN" : "ROLE_USER"; //PRECISA COLOCAR O PREFIXO "ROLE_"
        System.out.println("PRINCIPAL: " + principal + " ROLE: " + role);
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));        
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
        return result;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    
}

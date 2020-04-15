/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.dsa.springbootsoujava.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

/**
 *
 * @author dailtonalmeida
 */
@ConfigurationProperties
public class CustomUserDetailsContextMapper extends PersonContextMapper {

    @Value("${custom.ldap.adminGroup}")
    private String ldapAdminGroup;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        List<GrantedAuthority> mySystemAuthorities = new ArrayList<>();
        mySystemAuthorities.add(new SimpleGrantedAuthority("COMMON_USER"));

        for (GrantedAuthority authority: authorities) {
            if (ldapAdminGroup.equalsIgnoreCase(authority.getAuthority())) {
                mySystemAuthorities.add(new SimpleGrantedAuthority("ADMIN_USER"));
            }
        }
        
        return super.mapUserFromContext(ctx, username, mySystemAuthorities);
    }

    
}

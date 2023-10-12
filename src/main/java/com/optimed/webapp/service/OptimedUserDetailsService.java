package com.optimed.webapp.service;

import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.mappper.ObjectMapper;
import com.optimed.webapp.response.PrivilegeResponse;
import com.optimed.webapp.response.RoleResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class OptimedUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffClient staffClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        StaffResponse staff = staffClient.getStaffByEmail(username).getBody();

        if (staff == null) {
            return new org.springframework.security.core.userdetails
                    .User(
                    " ",
                    " ",
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(staffClient.getRoleByName("ROLE_USER").getBody())
           );
        }

        return new org.springframework.security.core.userdetails.User(
                staff.getEmail(), staff.getPassword(), true, true, true,
                true, getAuthorities(staff.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            RoleResponse role) {

        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(RoleResponse role) {
        List<String> privileges = new ArrayList<>();
        RoleResponse roleResponse = ObjectMapper.map(staffClient.getRoleByName(role.getName()).getBody(), RoleResponse.class);
        System.out.println(roleResponse);
        for(PrivilegeResponse p: roleResponse.getPrivileges())
            privileges.add(p.getName());
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

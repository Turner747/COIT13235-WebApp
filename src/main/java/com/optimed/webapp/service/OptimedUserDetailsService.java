package com.optimed.webapp.service;

import com.optimed.webapp.feignclient.StaffClient;
import com.optimed.webapp.response.RoleResponse;
import com.optimed.webapp.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                    getAuthorities(Arrays.asList(staffClient.getRoleByName("ROLE_USER").getBody()))
           );
        }

        return new org.springframework.security.core.userdetails.User(
                staff.getEmail(), staff.getPassword(), true, true, true,
                true, getAuthorities(Arrays.asList(staff.getRole())));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleResponse> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleResponse> roles) {

        /*List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;*/
        return new ArrayList<String>(); // placeholder
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        /*List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;*/
        return new ArrayList<GrantedAuthority>(); // placeholder
    }
}

/*
package com.example.parcel.entity;

public enum Role { ADMIN, CUSTOMER, DRIVER }

*/

package com.example.parcel.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    USER,
    ADMIN;

    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}


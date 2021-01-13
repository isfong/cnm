package com.isfong.cnm.access.identities.domain.models.entities;

import com.isfong.cnm.access.identities.domain.models.LoginOf;
import com.isfong.cnm.access.identities.sdk.models.Realm;
import com.isfong.cnm.shared.model.models.PersistableEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Entity
public class Principal extends PersistableEntity< Long > implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Enumerated
    private Realm realm;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Transient
    private LoginOf loginOf;

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities( ) {
        return null;
    }

    public Principal loginOf( LoginOf loginOf ) {
        this.loginOf = loginOf;
        return this;
    }
}

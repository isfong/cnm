package com.isfong.cnm.access.identities.application.services;

import com.isfong.cnm.access.identities.domain.models.LoginOf;
import com.isfong.cnm.access.identities.domain.models.entities.Principal;
import com.isfong.cnm.access.identities.domain.repositories.PrincipalRepository;
import com.isfong.cnm.access.identities.sdk.models.Realm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserDetailsDomainService implements UserDetailsService {
    private final HttpServletRequest httpServletRequest;
    private final PrincipalRepository principalRepository;

    public UserDetailsDomainService( HttpServletRequest httpServletRequest, PrincipalRepository principalRepository ) {
        this.httpServletRequest = httpServletRequest;
        this.principalRepository = principalRepository;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        Optional< LoginOf > loginOf = loginOf( );
        return this.principalRepository.findByUsername( username )
                .map( principal -> {
                    loginOf.ifPresent( principal::loginOf );
                    return principal;
                } )
                .orElseGet( ( ) -> loginOf.map( login -> new Principal( Realm.valueOf( ) ) )
                        .orElseThrow( ( ) -> new UsernameNotFoundException( username ) ) );
    }

    private Optional< LoginOf > loginOf( ) {
        String scope = httpServletRequest.getParameter( "scope" );
        try {
            return Optional.of( LoginOf.valueOf( scope ) );
        } catch ( Exception e ) {
            return Optional.empty( );
        }
    }

    private String realmUsernameFromClient( ) {
        httpServletRequest.getParameter( "client_id" ).split( "_" )[0]
    }
}

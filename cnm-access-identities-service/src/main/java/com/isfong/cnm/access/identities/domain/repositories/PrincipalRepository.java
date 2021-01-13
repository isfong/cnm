package com.isfong.cnm.access.identities.domain.repositories;

import com.isfong.cnm.access.identities.domain.models.entities.Principal;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrincipalRepository {
    Principal save( Principal principal );

    Optional<Principal> findByUsername( String username );
}

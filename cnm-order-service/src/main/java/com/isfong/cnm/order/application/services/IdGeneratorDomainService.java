package com.isfong.cnm.order.application.services;

import com.isfong.cnm.shared.model.utils.UUIDGenerator;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorDomainService {

    public String uuid( ) {
        return UUIDGenerator.newUUID( );
    }
}

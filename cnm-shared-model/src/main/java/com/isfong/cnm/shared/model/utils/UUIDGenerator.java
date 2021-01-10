package com.isfong.cnm.shared.model.utils;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class UUIDGenerator {
    private static final Base64.Encoder encoder = Base64.getUrlEncoder( );

    public static String newBase64UUID( ) {
        UUID uuid = UUID.randomUUID( );
        byte[] src = ByteBuffer.wrap( new byte[ 16 ] )
                .putLong( uuid.getMostSignificantBits( ) )
                .putLong( uuid.getLeastSignificantBits( ) )
                .array( );
        return encoder.encodeToString( src ).substring( 0, 22 );
    }

    public static String newUUID( ) {
        return UUID.randomUUID( ).toString( ).replace( "-", "" );
    }
}

package com.isfong.cnm.shared.model.representations;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor( access = AccessLevel.PRIVATE )
@AllArgsConstructor( access = AccessLevel.PRIVATE )
public class PagedRepresentations< T > {
    private int total;
    private int page;
    private List< T > representations;

    public static < T > PagedRepresentations< T > of( int total, int page, List< T > representations ) {
        return new PagedRepresentations<>( total, page, representations );
    }

    public static < T > PagedRepresentations< T > emptyResult( ) {
        return new PagedRepresentations<>( 0, 0, Collections.emptyList( ) );
    }

    public < K > PagedRepresentations< K > map( Function< T, K > fun ) {
        List< K > collect = representations.stream( ).parallel( ).map( fun ).collect( Collectors.toList( ) );
        return new PagedRepresentations<>( total, page, collect );
    }
}

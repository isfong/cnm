package com.isfong.cnm.product.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;
import com.isfong.cnm.product.domain.models.entities.Product;
import com.isfong.cnm.product.domain.repositories.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;

@Component
@SuppressWarnings( { "SqlDialectInspection", "SqlNoDataSourceInspection" } )
public class ProductJdbcPostgresqlRepository implements ProductRepository {
    private final ObjectMapper objectMapper = new ObjectMapper( );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcPostgresqlRepository( NamedParameterJdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save( Product product ) throws JsonProcessingException {
        String sql = "INSERT INTO CNM.PRODUCT (ID, JSON_CONTENT) VALUES (:id, :json) " +
                "ON CONFLICT(ID) DO UPDATE SET JSON_CONTENT=:json;";
        ImmutableBiMap< String, String > parameters = ImmutableBiMap.of( "id", product.getId( ), "json", objectMapper.writeValueAsString( product ) );
        this.jdbcTemplate.update( sql, parameters );
        return product;
    }

    @Override
    public List< Product > findAll( Pageable pageable ) {
        MapSqlParameterSource parameters = new MapSqlParameterSource( );
        parameters.addValue( "limit", pageable.getPageSize( ) );
        parameters.addValue( "offset", ( pageable.getPageNumber( ) - 1 ) * pageable.getPageSize( ) );
        final String SELECT_SQL = "SELECT JSON_CONTENT FROM CNM.PRODUCT LIMIT :limit OFFSET :offset;";
        return this.jdbcTemplate.query( SELECT_SQL, parameters, ( set, row ) -> {
            try {
                return objectMapper.readValue( set.getString( "json_content" ), Product.class );
            } catch ( JsonProcessingException e ) {
                e.printStackTrace( );
                return null;
            }
        } );
    }

    @Override
    public int findCount( ) {
        final String COUNT_SQL = "SELECT COUNT(1) FROM CNM.PRODUCT;";
        try {
            Integer count = this.jdbcTemplate.queryForObject( COUNT_SQL, newHashMap( ), Integer.class );
            return count == null ? 0 : count;
        } catch ( EmptyResultDataAccessException e ) {
            return 0;
        }
    }

    @Override
    public Optional< Product > findById( String id ) {
        final String SELECT_SQL = "SELECT JSON_CONTENT FROM CNM.PRODUCT WHERE ID=:id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource( );
        parameters.addValue( "id", id );
        Product product = this.jdbcTemplate.queryForObject( SELECT_SQL, parameters, ( set, row ) -> {
            try {
                return objectMapper.readValue( set.getString( "json_content" ), Product.class );
            } catch ( JsonProcessingException e ) {
                e.printStackTrace( );
                return null;
            }
        } );
        return Optional.ofNullable( product );
    }
}

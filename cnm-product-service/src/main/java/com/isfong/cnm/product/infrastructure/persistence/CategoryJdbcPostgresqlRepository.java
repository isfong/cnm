package com.isfong.cnm.product.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableBiMap;
import com.isfong.cnm.product.domain.models.entities.Category;
import com.isfong.cnm.product.domain.repositories.CategoryRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@SuppressWarnings( { "SqlDialectInspection", "SqlNoDataSourceInspection" } )
public class CategoryJdbcPostgresqlRepository implements CategoryRepository {
    private final ObjectMapper objectMapper = new ObjectMapper( );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryJdbcPostgresqlRepository( NamedParameterJdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category save( Category category ) throws JsonProcessingException {
        String sql = "INSERT INTO CNM.CATEGORY (ID, JSON_CONTENT) VALUES (:id, :json) " +
                "ON CONFLICT(ID) DO UPDATE SET JSON_CONTENT=:json;";
        ImmutableBiMap< String, String > parameters = ImmutableBiMap.of( "id", category.getId( ), "json", objectMapper.writeValueAsString( category ) );
        this.jdbcTemplate.update( sql, parameters );
        return category;
    }

    @Override
    public Optional< Category > findById( String id ) {
        final String SELECT_SQL = "SELECT JSON_CONTENT FROM CNM.CATEGORY WHERE ID=:id;";
        MapSqlParameterSource parameters = new MapSqlParameterSource( );
        parameters.addValue( "id", id );
        Category category = this.jdbcTemplate.queryForObject( SELECT_SQL, parameters, ( set, row ) -> {
            try {
                return objectMapper.readValue( set.getString( "json_content" ), Category.class );
            } catch ( JsonProcessingException e ) {
                e.printStackTrace( );
                return null;
            }
        } );
        return Optional.ofNullable( category );
    }

}

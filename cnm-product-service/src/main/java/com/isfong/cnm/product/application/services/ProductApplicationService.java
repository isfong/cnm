package com.isfong.cnm.product.application.services;

import com.isfong.cnm.order.sdk.representations.OrderItemRepresentation;
import com.isfong.cnm.product.domain.events.ProductDomainEventPublisher;
import com.isfong.cnm.product.domain.models.ProductNotFoundException;
import com.isfong.cnm.product.domain.models.entities.Product;
import com.isfong.cnm.product.domain.repositories.ProductRepository;
import com.isfong.cnm.product.sdk.events.ProductDomainEvent;
import com.isfong.cnm.product.sdk.representations.ProductRepresentation;
import com.isfong.cnm.shared.model.representations.PagedRepresentations;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductApplicationService {
    private final ProductRepository productRepository;
    private final ProductDomainEventPublisher productDomainEventPublisher;

    public ProductApplicationService( ProductRepository productRepository, ProductDomainEventPublisher productDomainEventPublisher ) {
        this.productRepository = productRepository;
        this.productDomainEventPublisher = productDomainEventPublisher;
    }

    @Transactional
    public String createProduct( String name, String description, BigDecimal price, String categoryId ) throws Exception {
        ResultWithDomainEvents< Product, ProductDomainEvent > resultWithDomainEvents = Product.create( name, description, price, categoryId );
        Product product = this.productRepository.save( resultWithDomainEvents.result );
        this.productDomainEventPublisher.publish( product, resultWithDomainEvents.events );
        return product.getId( );
    }

    public PagedRepresentations< ProductRepresentation > pagedRepresentations( int page, int size ) {
        List< Product > products = this.productRepository.findAll( PageRequest.of( page, size ) );
        int count = this.productRepository.findCount( );
        return PagedRepresentations.of( count, page, products.stream( ).map( Product::toRepresentation ).collect( Collectors.toList( ) ) );
    }

    public Optional< ProductRepresentation > getProduct( String id ) {
        return this.productRepository.findById( id ).map( Product::toRepresentation );
    }

    public void changeProductName( String id, String newName ) throws Exception {
        Product product = this.productRepository.findById( id ).orElseThrow( ( ) -> new ProductNotFoundException( id ) );
        ResultWithDomainEvents< Product, ProductDomainEvent > resultWithDomainEvents = product.changeName( newName );
        this.productRepository.save( resultWithDomainEvents.result );
        this.productDomainEventPublisher.publish( resultWithDomainEvents.result, resultWithDomainEvents.events );
    }

    public void validationOrderProducts( List< OrderItemRepresentation > items ) {
        items.forEach( item -> {
            Product product = this.productRepository.findById( item.getProductId( ) )
                    .orElseThrow( ( ) -> new ProductNotFoundException( item.getProductId( ) ) );
            product.validationInventory( item.getCount( ) );
        } );
    }

    @SneakyThrows
    public void changeProductInventory( String id, int remains )  {
        ResultWithDomainEvents< Product, ProductDomainEvent > inventoryChanged = this.productRepository.findById( id ).orElseThrow( ( ) -> new ProductNotFoundException( id ) )
                .changeInventory( remains );
        Product saved = this.productRepository.save( inventoryChanged.result );
        this.productDomainEventPublisher.publish( saved, inventoryChanged.events );
    }
}

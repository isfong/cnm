package com.isfong.cnm.order.infrastructure.persistence;

import com.isfong.cnm.order.domain.models.entities.Order;
import com.isfong.cnm.order.domain.repositories.OrderRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderJpaPersistence extends PagingAndSortingRepository< Order, String >, OrderRepository {
}

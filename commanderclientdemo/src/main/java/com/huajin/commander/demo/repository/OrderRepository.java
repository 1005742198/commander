package com.huajin.commander.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.huajin.commander.demo.domain.OrderEntity;


public interface OrderRepository extends CrudRepository<OrderEntity, Long>{

}

package com.cnu2016.repository;

import com.cnu2016.model.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Piyush on 7/8/16.
 */
public interface OrdersRepository extends CrudRepository<Orders, Integer>{
    Orders findByOrderId(int a);

}


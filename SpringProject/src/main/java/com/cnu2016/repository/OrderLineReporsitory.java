package com.cnu2016.repository;

import com.cnu2016.model.OrderLine;
import com.cnu2016.model.OrderLineKey;
import com.cnu2016.model.Orders;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Piyush on 7/9/16.
 */
public interface OrderLineReporsitory extends CrudRepository<OrderLine, OrderLineKey>
{
}

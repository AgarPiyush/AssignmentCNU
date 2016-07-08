package com.cnu2016.repository;

import java.util.List;

import com.cnu2016.model.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Integer>  {
    Product findByProductIdAndMark(int a, boolean b);
    List<Product> findByMark(boolean c);
}

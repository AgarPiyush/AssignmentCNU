package com.cnu2016.repository;

import com.cnu2016.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Piyush on 7/8/16.
 */

public interface UsersRepository extends CrudRepository<Users, Integer>{
    Users findByCustomerName(String a);

}

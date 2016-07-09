package com.cnu2016.controller;
/**
 * Created by Piyush on 7/7/16.
 */
import com.cnu2016.model.Product;
import com.cnu2016.repository.ProductRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    @Autowired
    ProductRepository productCrud;

    private ResponseEntity ifNullNotFound()
    {
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("detail", "Not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hmap);
    }


    @RequestMapping(value="api/products", method = RequestMethod.GET)
    public ResponseEntity retrieveProducts()
    {
        List<Product> products = productCrud.findByDiscontinued(false);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @RequestMapping(value="api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity getProductById(@PathVariable("id") int id)
    {
        Product p = productCrud.findByProductIdAndDiscontinued(id, false);
        if(p == null)
            return ifNullNotFound();
        return ResponseEntity.status(HttpStatus.OK).body(p);

    }
    @RequestMapping(value="api/products", method = RequestMethod.POST)
    public ResponseEntity postProduct(@RequestBody Product p)
    {
        productCrud.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @RequestMapping(value="api/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable("id") int id)
    {
        Product p = productCrud.findOne(id);
        if(p == null)
            return ifNullNotFound();

        p.setDiscontinued(true);
        productCrud.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    @RequestMapping(value="api/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProduct(@RequestBody Product p, @PathVariable("id") int id)
    {
        Product productExist = productCrud.findOne(id);
        if(productExist == null)
            return ifNullNotFound();

        p.setDiscontinued(false);
        p.setProductId(productExist.getProductId());
        productCrud.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    @RequestMapping(value = "api/products/{id}", method = RequestMethod.PATCH)
    public ResponseEntity patchProduct(@RequestBody Product p, @PathVariable("id") int id)
    {
        Product productExist = productCrud.findOne(id);
        if(productExist == null || productExist.isDiscontinued() == true)
            return ifNullNotFound();

        if(p.getProductCode() != null)
            productExist.setProductCode(p.getProductCode());
        if(p.getProductDescription() != null)
            productExist.setProductDescription(p.getProductDescription());
        productCrud.save(productExist);
        return ResponseEntity.status(HttpStatus.OK).body(productExist);
    }
}
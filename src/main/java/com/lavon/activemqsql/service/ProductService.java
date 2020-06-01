package com.lavon.activemqsql.service;

import com.lavon.activemqsql.api.v1.model.ProductDTO;
import com.lavon.activemqsql.domain.Product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO saveOrUpdate(ProductDTO product);

    long count();

    void deleteById(Long id);

//    ActiveMQ message
    void sendMessage(Long id);

    Boolean updateCount(Long id);
}

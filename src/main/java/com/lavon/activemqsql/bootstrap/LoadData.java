package com.lavon.activemqsql.bootstrap;

import com.lavon.activemqsql.domain.Product;
import com.lavon.activemqsql.respository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class LoadData implements CommandLineRunner {

    private final ProductRepository productRepository;

    public LoadData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count()>0)
            productRepository.deleteAll();
        LoadProducts();
    }

    private void LoadProducts(){

        Product product1 = Product.builder().description("dscr1").price(BigDecimal.valueOf(18)).messageCount(0).build();
        productRepository.save(product1);

        Product product2 = Product.builder().description("dscr2").price(BigDecimal.valueOf(15)).messageCount(0).build();
        productRepository.save(product2);

        log.info("Product loaded");
    }
}

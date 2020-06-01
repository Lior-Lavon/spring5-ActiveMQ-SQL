package com.lavon.activemqsql.service;

import com.lavon.activemqsql.api.v1.mapper.ProductMapper;
import com.lavon.activemqsql.api.v1.model.ProductDTO;
import com.lavon.activemqsql.config.JmsConfig;
import com.lavon.activemqsql.domain.Product;
import com.lavon.activemqsql.respository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final JmsTemplate jmsTemplate;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository, JmsTemplate jmsTemplate) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                   ProductDTO productDTO = productMapper.productToProductDTO(product);
                   return productDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            return productDTO;
        } else{
            return null;
        }
    }

    @Override
    public ProductDTO saveOrUpdate(ProductDTO productDTO) {

        Product product = productMapper.productDTOToProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        ProductDTO savesProductDTO = productMapper.productToProductDTO(savedProduct);

        return savesProductDTO;
    }


    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void sendMessage(Long id) {

        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("id", String.valueOf(id));

        log.info("Sending the index request through queue message");
        jmsTemplate.convertAndSend(JmsConfig.PRODUCT_MESSAGE_QUEUE, actionMap);
    }

    @Override
    public Boolean updateCount(Long id){
        ProductDTO productDTO = findById(id);
        if(productDTO!=null){
            int count = productDTO.getMessageCount();
            productDTO.setMessageCount(++count);
            productDTO.setMessageReceived(true);
            saveOrUpdate(productDTO);
            return true;
        } else
            return false;
    }
}

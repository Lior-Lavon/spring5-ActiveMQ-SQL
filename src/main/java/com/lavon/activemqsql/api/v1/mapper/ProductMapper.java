package com.lavon.activemqsql.api.v1.mapper;

import com.lavon.activemqsql.api.v1.model.ProductDTO;
import com.lavon.activemqsql.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE =Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);
    Product productDTOToProduct(ProductDTO productDTO);
}

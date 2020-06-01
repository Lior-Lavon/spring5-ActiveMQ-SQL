package com.lavon.activemqsql.controller;

import com.lavon.activemqsql.api.v1.model.ProductDTO;
import com.lavon.activemqsql.domain.Product;
import com.lavon.activemqsql.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String redirectToList(){
        return "redirect:/product/list";
    }

    @GetMapping("/product/list")
    public String getProductList(Model model){

        List<ProductDTO> productDTOList = productService.findAll();
        model.addAttribute("products", productDTOList);

        return "product/list";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model){
        ProductDTO productDTO = ProductDTO.builder().build();
        model.addAttribute("productForm", productDTO);
        return "product/productform";
    }

    @GetMapping("/product/show/{id}")
    public String showProduct(@PathVariable Long id, Model model){
        ProductDTO productDTO = productService.findById(id);

        model.addAttribute("product", productDTO);
        return "product/show";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        ProductDTO productDTO = productService.findById(id);

        model.addAttribute("productForm", productDTO);
        return "product/productform";
    }

    @PostMapping("/product")
    public String editProduct(ProductDTO productDTO ,Model model){

        ProductDTO savedProductDTO = productService.saveOrUpdate(productDTO);
        model.addAttribute("product", savedProductDTO);
        return "product/show";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteById(id);

        return "redirect:/product/list";
    }

    @GetMapping("/product/sendMessage/{id}")
    public String sendMessage(@PathVariable Long id){

        productService.sendMessage(id);
        // redirect to same page
        return "redirect:/product/show/" + id;
    }


}

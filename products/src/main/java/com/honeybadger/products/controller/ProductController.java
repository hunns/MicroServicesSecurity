package com.honeybadger.products.controller;


import com.honeybadger.products.entity.Product;
import com.honeybadger.products.requestsResposes.ProductResponse;
import com.honeybadger.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping ("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private final Logger LOGGER= Logger.getLogger("ProductController");
    @GetMapping()
    private List<Product> findAll(){
        LOGGER.info("Product find all called..");
    return this.productService.getAllProducts();
    }

    @GetMapping("/{id}")
    private ProductResponse find(@PathVariable Long id){
        LOGGER.info("Product find all called..");
        return this.productService.getProduct(id);
    }
    @PostMapping()
    private Product saveProduct(@RequestBody Product product){
        LOGGER.info("Product save called.."+ product);
        return this.productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    private ProductResponse updateProduct(@PathVariable Long id, @RequestBody Product product){
        LOGGER.info("Product update called.."+ product);
       return this.productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    private String DeleteProduct(@PathVariable Long id){
        LOGGER.info("Product Delete called.."+ id);
         this.productService.deleteProduct(id);
         return "Deleted";
    }

}

package com.honeybadger.shopping.controller;

import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductPlusProductCategory;
import com.honeybadger.shopping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping ("/shopping/product")
public class ProductController {


    @Autowired
    private ProductService productService;
    private final Logger LOGGER= Logger.getLogger("ProductController");
    @GetMapping()
    private List<Product> findAll(){
        LOGGER.info("Product find all called..");
        return this.productService.getAllProductsWithoutProductCategory();
    }

    @GetMapping("/{id}")
    private Product find(@PathVariable Long id){
        LOGGER.info("Product find all called..");
        return this.productService.getProductById(id);
    }

    @GetMapping("/category/")
    private List<ProductPlusProductCategory> getProductsWithCategory(){
        LOGGER.info("Products with category are displayed");
        return this.productService.getAllProductsWithProductCategory();

    }
    @PostMapping()
    private ResponseEntity<ProductPlusProductCategory> saveProduct(@RequestBody ProductPlusProductCategory productPlusProductCategory){
        LOGGER.info("Product save called.."+ productPlusProductCategory);
        return this.productService.saveProductPlusProductCategory(productPlusProductCategory);
    }

    @PutMapping()
    private ResponseEntity<ProductPlusProductCategory> updateProduct( @RequestBody ProductPlusProductCategory product){
        LOGGER.info("Product update called.."+ product);
        return this.productService.updateProductWithProductCategory(product);
    }

    @DeleteMapping("/{id}")
    private String DeleteProduct(@PathVariable Long id){
        LOGGER.info("Product Delete called.."+ id);
        if (this.productService.deleteProductById(id)) {
            return "Deleted";
        }
        return "Product with id "+ id +" not found";
    }


}

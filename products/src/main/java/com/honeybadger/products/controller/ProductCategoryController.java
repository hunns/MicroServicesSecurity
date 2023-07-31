package com.honeybadger.products.controller;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import com.honeybadger.products.requestsResposes.ProductCategoryResponse;
import com.honeybadger.products.requestsResposes.ProductResponse;
import com.honeybadger.products.service.ProductCategoryService;
import com.honeybadger.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping ("/products/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    private final Logger LOGGER= Logger.getLogger("ProductCategoryController");
    @GetMapping()
    private List<ProductCategory> findAll(){
        LOGGER.info("-------------->Product Category find all called..");
    return this.productCategoryService.getAllProductCategories();
    }

    @GetMapping("/{id}")
    private ProductCategoryResponse find(@PathVariable Long id){
        LOGGER.info("ProductCategory find all called..");
        return this.productCategoryService.getProductCategory(id);
    }
    @PostMapping()
    private ProductCategory saveProduct(@RequestBody ProductCategory productCategory){
        LOGGER.info("Product save called.."+ productCategory);
        return this.productCategoryService.saveProductCategory(productCategory);
    }

    @PutMapping("/{id}")
    private ProductCategoryResponse updateProductCategory(@PathVariable Long id, @RequestBody ProductCategory productCategory){
        LOGGER.info("Product update called.."+ productCategory);
       return this.productCategoryService.updateProductCategory(id, productCategory);
    }

    @DeleteMapping("/{id}")
    private String DeleteProductCategory(@PathVariable Long id){
        LOGGER.info("Product Delete called.."+ id);
         this.productCategoryService.deleteProductCategory(id);
         return "Deleted";
    }

}

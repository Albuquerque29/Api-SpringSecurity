package controller;

import dto.ProductDto;
import model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;
import service.GetProductByIdUseCase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        Product savedProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsByPrefix(){
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID id){
        Product product = getProductByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id){
        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        productRepository.delete(foundProduct.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductById(@RequestBody ProductDto productDto,
                                                    @PathVariable UUID id){
        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        Product product = foundProduct.get();
        BeanUtils.copyProperties(productDto, product);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
}
}
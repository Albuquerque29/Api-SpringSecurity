package com.example.Projeto.Back.End.controller;

import com.example.Projeto.Back.End.dto.ProductDto;
import com.example.Projeto.Back.End.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Projeto.Back.End.repository.ProductRepository;
import com.example.Projeto.Back.End.service.GetProductByIdUseCase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "Endpoints relacionados a produtos")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;

    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content(schema = @Schema(implementation = Product.class)))
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        Product savedProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @Operation(summary = "Listar produtos", description = "Lista todos os produtos disponíveis.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByPrefix() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID.")
    @ApiResponse(responseCode = "200", description = "Produto retornado com sucesso", content = @Content(schema = @Schema(implementation = Product.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID id) {
        Product product = getProductByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "Excluir produto", description = "Exclui um produto específico pelo ID.")
    @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
        productRepository.delete(foundProduct.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletetado com successo!");
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza as informações de um produto pelo ID.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(schema = @Schema(implementation = Product.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@RequestBody ProductDto productDto, @PathVariable UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }

        Product product = foundProduct.get();
        BeanUtils.copyProperties(productDto, product);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
    }
}

package service;

import exception.ProductNotFoundException;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductByIdUseCase {

    @Autowired
    private static ProductRepository productRepository;

    public static Product execute(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return optionalProduct.get();
    }
}
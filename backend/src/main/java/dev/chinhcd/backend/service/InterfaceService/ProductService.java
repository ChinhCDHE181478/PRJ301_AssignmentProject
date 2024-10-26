package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.models.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Set<Product> getAllProducts();

    Optional<Product> getProduct(int id);
}

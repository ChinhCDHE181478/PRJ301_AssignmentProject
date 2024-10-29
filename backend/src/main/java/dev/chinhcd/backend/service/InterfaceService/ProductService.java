package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.ProductResponse;
import dev.chinhcd.backend.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    Optional<Product> getProduct(int id);
}

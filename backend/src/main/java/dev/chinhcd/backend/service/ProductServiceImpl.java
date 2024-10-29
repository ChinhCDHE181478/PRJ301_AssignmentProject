package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.ProductResponse;
import dev.chinhcd.backend.models.Product;
import dev.chinhcd.backend.repository.ProductRepository;
import dev.chinhcd.backend.service.InterfaceService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getProduct(int id) {
        return productRepository.findById(id);
    }
}

package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.Product;
import dev.chinhcd.backend.repository.ProductRepository;
import dev.chinhcd.backend.service.InterfaceService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public Set<Product> getAllProducts() {
        return new HashSet<>(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getProduct(int id) {
        return productRepository.findById(id);
    }
}

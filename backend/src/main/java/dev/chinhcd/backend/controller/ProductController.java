package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.responses.ProductResponse;
import dev.chinhcd.backend.service.InterfaceService.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}

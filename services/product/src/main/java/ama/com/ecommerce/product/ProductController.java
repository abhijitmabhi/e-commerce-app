package ama.com.ecommerce.product;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
    return ResponseEntity.ok(productService.createProduct(request));
  }

  @PostMapping("/purchase")
  public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(
      @RequestBody @Valid List<ProductPurchaseRequest> requests) {
    return ResponseEntity.ok(productService.purchaseProducts(requests));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(productService.findAllProducts());
  }

  @GetMapping("/{product-id}")
  public ResponseEntity<ProductResponse> getProductById(
      @PathVariable("product-id") Integer productId) {
    return ResponseEntity.ok(productService.findProductById(productId));
  }
}

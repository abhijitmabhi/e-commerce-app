package ama.com.ecommerce.product;

import ama.com.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public Integer createProduct(ProductRequest request) {
    var product = productMapper.toProduct(request);
    return productRepository.save(product).getId();
  }

  public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) {
    var productIds = requests.stream().map(ProductPurchaseRequest::productId).toList();
    var storedProducts = productRepository.findAllByIdInOrderById(productIds);
    if (productIds.size() != storedProducts.size()) {
      throw new ProductPurchaseException("One or more product does not exists");
    }

    var storedRequest =
        requests.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
    var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

    for (int i = 0; i < storedProducts.size(); i++) {
      Product product = storedProducts.get(i);
      ProductPurchaseRequest productPurchaseRequest = storedRequest.get(i);
      if (product.getAvailableQuantity() < productPurchaseRequest.quantity()) {
        throw new ProductPurchaseException(
            "Product does not have sufficient quantity with productID:: " + product.getId());
      }
      var newAvailableQuantity = product.getAvailableQuantity() - productPurchaseRequest.quantity();
      product.setAvailableQuantity(newAvailableQuantity);
      productRepository.save(product);
      purchasedProducts.add(
          productMapper.toProductPurchaseResponse(product, productPurchaseRequest.quantity()));
    }

    return purchasedProducts;
  }

  public List<ProductResponse> findAllProducts() {
    return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
  }

  public ProductResponse findProductById(Integer productId) {
    return productRepository
        .findById(productId)
        .map(productMapper::toProductResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("Product not found with the ID:: " + productId));
  }
}

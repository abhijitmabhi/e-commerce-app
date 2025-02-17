package ama.com.ecommerce.order.product;

import ama.com.ecommerce.product.PurchaseRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service", url = "${application.config.product-url}")
public interface ProductClient {

  @GetMapping("/purchase")
  Optional<PurchaseResponse> purchaseProduct(List<PurchaseRequest> requests);
}

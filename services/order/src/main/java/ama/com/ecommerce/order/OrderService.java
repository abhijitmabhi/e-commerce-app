package ama.com.ecommerce.order;

import ama.com.ecommerce.exception.BusinessException;
import ama.com.ecommerce.order.customer.CustomerClient;
import ama.com.ecommerce.order.product.ProductClient;
import ama.com.ecommerce.orderline.OrderLineRequest;
import ama.com.ecommerce.orderline.OrderLineService;
import ama.com.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CustomerClient customerClient;
  private final ProductClient productClient;
  private final OrderMapper orderMapper;
  private final OrderLineService orderLineService;

  public Integer createOrder(@Valid OrderRequest request) {

    var customer =
        customerClient
            .getCustomerById(request.customerId())
            .orElseThrow(
                () ->
                    new BusinessException(
                        "Can not create order:: No customer exists with the provided ID: "
                            + request.customerId()));

    productClient.purchaseProduct(request.products());

    var order = orderRepository.save(orderMapper.toOrder(request));

    for (PurchaseRequest product : request.products()) {
      orderLineService.saveOrderLine(
          new OrderLineRequest(null, order.getId(), product.productId(), product.quantity()));
    }

    // todo: start payment process

    // todo: send the order confirmation --> notification-ms (kafka)
    return null;
  }
}

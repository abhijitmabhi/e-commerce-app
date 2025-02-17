package ama.com.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
    @NotNull(message = "Product ID is mandatory") Integer productId,
    @Positive(message = "Quantity should be greater than zero") double quantity) {}

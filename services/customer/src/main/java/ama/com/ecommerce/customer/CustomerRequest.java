package ama.com.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstname is mandatory")
        String firstName,
        @NotNull(message = "Customer lastName is mandatory")
        String lastName,
        @NotNull(message = "Customer email is mandatory")
        @Email(message = "Customer email is not valid")
        String email,
        Address address
) {
}

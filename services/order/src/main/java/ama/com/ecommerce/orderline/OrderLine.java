package ama.com.ecommerce.orderline;

import ama.com.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "order_line")
public class OrderLine {

  @Id @GeneratedValue private Integer id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  private Integer productId;
  private double quantity;
}

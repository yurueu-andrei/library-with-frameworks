package by.library.yurueu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends BaseEntity {
    private Long id;
    private OrderStatus orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;

    @Builder
    public Order(Long id, OrderStatus orderStatus, LocalDate startDate, LocalDate endDate, int price, Long userId) {
        setId(id);
        this.orderStatus = orderStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.userId = userId;
    }
}
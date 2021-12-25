package model;

import lombok.Data;
import model.enumeration.OrderStatus;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Mahsa Alikhani m-58
 */
@Data
@Entity
public class Order {
    @Id
    private int id;
    private int quantity;
    private double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int counter;
    @OneToOne
    private Product product;
    @ManyToOne
    private Customer customer;

}

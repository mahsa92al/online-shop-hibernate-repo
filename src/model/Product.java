package model;

import lombok.Data;
import model.enumeration.ProductCategory;

import javax.persistence.*;

/**
 * @author Mahsa Alikhani m-58
 */
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}

package service;

import model.Product;
import repository.ProductDao;

import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class ProductService {
    private final ProductDao productDao;

    public ProductService(){
        this.productDao = new ProductDao();
    }
    public List<Product> getAllProducts(){
        List<Product> products = productDao.findAllProducts();
        return products;
    }
}

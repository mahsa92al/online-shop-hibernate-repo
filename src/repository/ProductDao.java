package repository;

import model.Order;
import model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


/**
 * @author Mahsa Alikhani m-58
 */
public class ProductDao extends BaseDao{

    public List<Product> findAllProducts(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Product> hql = session.createQuery("from Order");
        List<Product> products = hql.getResultList();
        transaction.commit();
        session.close();
        return products;
    }

    public Double findItemPriceById(int id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Product> hql = session.createQuery("from Product p where p.id=:productId");
        hql.setParameter("productId", id);
        Product result = hql.getSingleResult();
        transaction.commit();
        session.close();
        return result.getPrice();
    }

    public int findStockByProductId(int id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int stock = (int) session.createQuery("from Product p" +
                " where p.id =: productId").setParameter("productId", id).getSingleResult();
        transaction.commit();
        session.close();
        return stock;
    }

    public int findProductIdByOrderId(int orderId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.id =: orderId");
        hql.setParameter("orderId", orderId);
        Order order = hql.getSingleResult();
        transaction.commit();
        session.close();
        return order.getProduct().getId();
    }

    public void updateProductStock(int id, int newStock) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Product> hql = session.createQuery("from Product p where p.id=:productId");
        hql.setParameter("productId", id);
        Product product = hql.getSingleResult();
        product.setStock(newStock);
        session.saveOrUpdate(product);
        transaction.commit();
        session.close();
    }
}

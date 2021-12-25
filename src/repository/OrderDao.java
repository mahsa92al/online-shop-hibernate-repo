package repository;

import model.Customer;
import model.Order;
import model.enumeration.OrderStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class OrderDao extends BaseDao{

    public void saveNewOrder(Order order){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.id =: orderId");
        hql.setParameter("orderId", orderId);
        Order order = hql.getSingleResult();
        order.setStatus(status);
        session.saveOrUpdate(order);
        transaction.commit();
        session.close();
    }

    public void deleteAnOrderByOrderId(int orderId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.id =: orderId");
        hql.setParameter("orderId", orderId);
        Order order = hql.getSingleResult();
        session.remove(order);
        transaction.commit();
        session.close();
    }

    public double findTotalOrdersPrice(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orders = findAllOrders(customer);
        double sumPrice = 0;
        for (int i = 0; i < orders.size(); i++){
            sumPrice = sumPrice + orders.get(i).getTotalPrice();
        }
        transaction.commit();
        session.close();
        return sumPrice;
    }

    public int findMaxOrderCounterByCustomerId(Customer customer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int maxCounter = (int) session.createQuery("select max(counter) from Order o" +
                " where o.customer =: customer").setParameter("customer", customer)
                .getSingleResult();
        transaction.commit();
        session.close();
        return maxCounter;
    }

    public OrderStatus findOrderStatusById(int orderId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.id=:orderId");
        hql.setParameter("orderId", orderId);
        Order order = hql.getSingleResult();
        transaction.commit();
        session.close();
        return order.getStatus();
    }

    public void updateOrderCounter(int orderId, int newCounter){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.id=:orderId");
        hql.setParameter("orderId", orderId);
        Order order = hql.getSingleResult();
        order.setCounter(newCounter);
        session.saveOrUpdate(order);
        transaction.commit();
        session.close();
    }

    public List<Order> findAllOrders(Customer customer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Order> hql = session.createQuery("from Order o where o.customer=:customer");
        hql.setParameter("customer", customer);
        List<Order> orders = hql.getResultList();
        transaction.commit();
        session.close();
        return orders;
    }
}

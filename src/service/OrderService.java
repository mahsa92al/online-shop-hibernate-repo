package service;

import model.Customer;
import model.Order;
import model.enumeration.OrderStatus;
import repository.OrderDao;
import repository.ProductDao;

import java.sql.Date;
import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;

    public OrderService() {
        orderDao = new OrderDao();
        productDao = new ProductDao();
    }

    public boolean addNewOrderToBag(int itemId, int quantity, Date date, Customer customer) throws Exception {
        boolean isFull = false;
        int maxCounter = orderDao.findMaxOrderCounterByCustomerId(customer);
        if (maxCounter < 5) {
            int counter = maxCounter + 1;
            int stock = productDao.findStockByProductId(itemId);
            if (stock == 0) {
                throw new Exception("The product stock is zero.");
            } else if (quantity > stock){
                throw new Exception("The order stock is " + stock + " please choose order quantity < " +stock);
            }else {
                double price = productDao.findItemPriceById(itemId);
                double totalPrice = price * quantity;
                Order order = new Order();
                order.setId(itemId);
                order.setQuantity(quantity);
                order.setTotalPrice(totalPrice);
                order.setDate(date);
                order.setCustomer(customer);
                order.setStatus(OrderStatus.NOT_CONFIRMED);
                order.setCounter(counter);
                orderDao.saveNewOrder(order);
            }
        } else {
            isFull = true;
        }
        return isFull;
    }

    public double sumOfOrderPrices(Customer customer) {
        double sumPrice = orderDao.findTotalOrdersPrice(customer);
        return sumPrice;
    }

    public void confirmOrdersByCustomer(List<Order> ordersList) {
        for (Order item : ordersList) {
            if (item.getStatus() == OrderStatus.NOT_CONFIRMED) {
                orderDao.updateOrderStatus(item.getId(), OrderStatus.CONFIRMED);
                int productId = productDao.findProductIdByOrderId(item.getId());
                int stock = productDao.findStockByProductId(productId);
                int newStock = stock - item.getQuantity();
                productDao.updateProductStock(productId, newStock);
            }
        }
    }

    public List<Order> getOrderList(Customer customer){
        List<Order> orders = orderDao.findAllOrders(customer);
        return orders;
    }

    public void removeOrderFromBag(int orderId)  {
        if(orderDao.findOrderStatusById(orderId) == OrderStatus.NOT_CONFIRMED){
            orderDao.deleteAnOrderByOrderId(orderId);
        }
    }

    public void decreaseOrderCounter(int orderId, int newCounter) {
        orderDao.updateOrderCounter(orderId, newCounter);
    }
}

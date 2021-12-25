package repository;

import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * @author Mahsa Alikhani m-58
 */
public class CustomerDao extends BaseDao{

    public Integer saveNewCustomer(Customer customer){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Integer customerId = (Integer)session.save(customer);
        transaction.commit();
        session.close();
        return customerId;
    }
}

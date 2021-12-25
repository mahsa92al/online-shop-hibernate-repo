package service;

import model.Customer;
import repository.CustomerDao;

/**
 * @author Mahsa Alikhani m-58
 */
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(){
        customerDao = new CustomerDao();
    }

    public int addNewCustomer(Customer customer){
    return customerDao.saveNewCustomer(customer);
    }
}

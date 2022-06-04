package training.exception;

import training.domain.Customer;
import training.service.AppConstant;


public class InvalidCustomerException extends ApplicationException{

    private Customer customer;

    public InvalidCustomerException(Customer customer, String message) {
        super(message);
        this.customer = customer;
    }

    @Override
    public String getErrorCode() {
        return AppConstant.INVALID_CUSTOMER;
    }

    public Customer getCustomer() {
        return this.customer;
    }
}

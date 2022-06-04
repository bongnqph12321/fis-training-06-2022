package training.exception;

import training.domain.Customer;
import training.service.AppConstant;


public class InvalidCustomerStatusException extends  ApplicationException{
    private final Customer customer;
    public InvalidCustomerStatusException(Customer customer,String message) {
        super(message);
        this.customer = customer;
    }
    @Override
    public String getErrorCode() {
        return AppConstant.INVALID_STATUS_CUSTOMER;
    }
}

package training.service;

import training.domain.Customer;
import training.exception.DuplicateCustomerException;
import training.exception.InvalidCustomerException;
import training.store.InMemoryCustomerStore;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleCustomerService implements CustomerService{

    private static List<Customer> list = new ArrayList<>();
    private InMemoryCustomerStore customerStore;

    @Override
    public Customer findById(String id) {

//        LOG.info("Find Account By Id: {}", id);
//        Customer foundAccount = Customer.get(id);
//        if(foundAccount==null) {
//            LOG.info("Not found account with Id: {}. Return Null value instate", id);
//        }
//        return foundAccount;

        Optional<Customer> opt = list.stream().filter(item->item.getId()==id).findFirst();
        if(opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        validate(customer);
        checkDuplicate(customer);
        return customerStore.insertOrUpdate(customer);
    }

    private void checkDuplicate(Customer customer) {
        if(customerStore.findAll().stream().filter(c -> {
            return c.getMobile().equals(customer.getMobile());
        }).findFirst().isPresent())
            throw  new DuplicateCustomerException(customer,String.format(
                    "Customer with phone number %s is duplicate",customer.getMobile()));
    }

    private void validate(Customer customer) {
        if(!isNullOrEmpty(customer.getName())) {
            throw new InvalidCustomerException(customer,"Customer name is empty");
        }

        if(!isNullOrEmpty(customer.getMobile())) {
            throw new InvalidCustomerException(customer,"Mobile is empty");
        }
    }

    private boolean isNullOrEmpty(String id) {
        if(id == null) return false;
        if(id.trim().length() == 0) return false;
        return true;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        for(int i = 0;i<list.size();i++) {
            Customer  item = list.get(i);
            if(item.getId()==customer.getId()) {
                list.set(0, customer);
                break;
            }
        }
        return null;
    }

    @Override
    public void deleteCustomerById(String id) {
        list.removeIf(item->item.getId()==id);
    }

    @Override
    public List<Customer> findAllOrderByNameAsc() {

        return customerStore.findAll().stream()
                .sorted(Comparator.comparing(Customer ::getName)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAllOrderByNameLimit(int limit) {
        return customerStore.findAll().stream()
                .sorted(Comparator.comparing(Customer::getName))
                .limit(limit).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAllCustomerByNameLikeOrderByNameAsc(String custName, String limit) {
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return null;
    }

    @Override
    public List<SummaryCustomerByAgeDTO> summaryCustomerByAgeOrderByAgeDesc() {
        //TODO: Implement method tho dung dac ta cua CustomerService interface
        return null;
    }

}

package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerFilter {
    private List<Customer> customers;

    public CustomerFilter(List<Customer> customers) {
        this.customers = customers;
    }

    public CustomerFilter withName(String name) {
        if (name == null) return this;
        customers = customers.stream().filter(c -> c.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        return this;
    }

    public CustomerFilter withPhone(String phone) {
        if (phone == null) return this;
        customers = customers.stream().filter(c -> c.getPhone().equalsIgnoreCase(phone)).collect(Collectors.toList());
        return this;
    }

    public CustomerFilter withAddress(String address) {
        if (address == null) return this;
        customers = customers.stream().filter(c -> c.getAddress().equalsIgnoreCase(address)).collect(Collectors.toList());
        return this;
    }

    public List<Customer> get() {
        return customers;
    }

}

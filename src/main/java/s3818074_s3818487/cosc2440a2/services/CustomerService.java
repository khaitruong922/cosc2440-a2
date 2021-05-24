package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import s3818074_s3818487.cosc2440a2.filters.CustomerFilter;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CustomerService extends AbstractService<Customer, UUID> {

    private final SalesInvoiceRepository salesInvoiceRepository;

    @Autowired
    public CustomerService(CustomerRepository repo, SalesInvoiceRepository salesInvoiceRepository) {
        super(repo);
        this.salesInvoiceRepository = salesInvoiceRepository;
    }

    @Override
    public Customer updateById(Customer updatedCustomer, UUID id) {
        Optional<Customer> customerOptional = repo.findById(id);
        if (customerOptional.isEmpty()) throw new RuntimeException("Customer not found");
        Customer customer = customerOptional.get();
        customer.setName(Optional.ofNullable(updatedCustomer.getName()).orElse(customer.getName()));
        customer.setAddress(Optional.ofNullable(updatedCustomer.getAddress()).orElse(customer.getAddress()));
        customer.setFax(Optional.ofNullable(updatedCustomer.getPhone()).orElse(customer.getPhone()));
        customer.setEmail(Optional.ofNullable(updatedCustomer.getEmail()).orElse(customer.getEmail()));
        customer.setContactPerson(Optional.ofNullable(updatedCustomer.getContactPerson()).orElse(customer.getContactPerson()));
        return customer;
    }

    public List<Customer> search(String name, String phone, String address, Integer page) {
        return new CustomerFilter(super.getAll(page)).withName(name).withPhone(phone).withAddress(address).get();
    }

    public Double getRevenue(UUID id, Date startDate, Date endDate) {
        List<SalesInvoice> salesInvoices = new SalesInvoiceFilter(salesInvoiceRepository.findAll()).ofStaff(id).start(startDate).end(endDate).get();
        return salesInvoices.stream().mapToDouble(SalesInvoice::getTotalValue).sum();
    }

    public List<SalesInvoice> getSalesInvoices(UUID id, Date startDate, Date endDate, Integer page) {
        final int RESULTS_PER_PAGE = 5;
        return new SalesInvoiceFilter(salesInvoiceRepository.findAll(PageRequest.of(page, RESULTS_PER_PAGE)).toList()).ofCustomer(id).start(startDate).end(endDate).get();
    }
}

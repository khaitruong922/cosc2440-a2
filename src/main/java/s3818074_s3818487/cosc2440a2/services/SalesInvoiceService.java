package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Customer;
import s3818074_s3818487.cosc2440a2.models.SalesDetail;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.CustomerRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class SalesInvoiceService extends AbstractService<SalesInvoice, UUID> {
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final SalesDetailRepository salesDetailsRepository;

    @Autowired
    public SalesInvoiceService(SalesInvoiceRepository repo, StaffRepository staffRepository, CustomerRepository customerRepository, SalesDetailRepository salesDetailsRepository) {
        super(repo);
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
        this.salesDetailsRepository = salesDetailsRepository;
    }

    @Override
    public SalesInvoice add(SalesInvoice salesInvoice) {
        // Handle staff
        Staff staff = salesInvoice.getStaff();
        if (staff == null) throw new RuntimeException("Missing staff argument!");
        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");
        salesInvoice.setStaff(staffOptional.get());


        // Handle customer
        Customer customer = salesInvoice.getCustomer();
        if (customer == null) throw new RuntimeException("Missing customer argument!");
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());
        if (customerOptional.isEmpty()) throw new RuntimeException("Customer not found!");
        salesInvoice.setCustomer(customerOptional.get());

        // Handle sales details
        List<SalesDetail> salesDetails = new ArrayList<>();
        AtomicReference<Double> totalValue = new AtomicReference<>((double) 0);
        if (salesInvoice.getSalesDetails() == null) salesInvoice.setSalesDetails(Collections.emptyList());
        salesInvoice.getSalesDetails().forEach(sd -> {
            Optional<SalesDetail> salesDetailOptional = salesDetailsRepository.findById(sd.getId());
            if (salesDetailOptional.isEmpty()) throw new RuntimeException("Sales detail not found!");
            SalesDetail salesDetail = salesDetailOptional.get();
            if (salesDetail.getSalesInvoice() != null)
                throw new RuntimeException("Sales detail " + salesDetail.getId() + " has been used!");
            totalValue.updateAndGet(v -> v + salesDetail.getPrice() * salesDetail.getQuantity());
            salesDetail.setSalesInvoice(salesInvoice);
            salesDetails.add(salesDetail);
        });

        // Set attributes
        salesInvoice.setSalesDetails(salesDetails);
        salesInvoice.setTotalValue(totalValue.get());

        return super.add(salesInvoice);
    }

    @Override
    public SalesInvoice updateById(SalesInvoice updatedSalesInvoice, UUID id) {
        Optional<SalesInvoice> salesInvoiceOptional = repo.findById(id);
        if (salesInvoiceOptional.isEmpty()) throw new RuntimeException("Sales invoice not found!");
        SalesInvoice salesInvoice = salesInvoiceOptional.get();


        // Handle sales details update
        if (salesInvoice.getSalesDetails() != null) {
            AtomicReference<Double> totalValue = new AtomicReference<>((double) 0);
            List<SalesDetail> salesDetails = new ArrayList<>();
            updatedSalesInvoice.getSalesDetails().forEach(sd -> {
                // Set the sales invoice of all current details to null
                salesInvoice.getSalesDetails().forEach(d -> d.setSalesInvoice(null));

                Optional<SalesDetail> salesDetailOptional = salesDetailsRepository.findById(sd.getId());
                if (salesDetailOptional.isEmpty()) throw new RuntimeException("Sales detail not found");
                SalesDetail salesDetail = salesDetailOptional.get();
                // Check if the sales detail does not belong to other order
                if (salesDetail.getSalesInvoice() != null)
                    throw new RuntimeException("Sales detail " + salesDetail.getId() + " has been used!");

                totalValue.updateAndGet(v -> v + salesDetail.getPrice() * salesDetail.getQuantity());
                salesDetail.setSalesInvoice(salesInvoice);
                salesDetails.add(salesDetail);
            });
            salesInvoice.setSalesDetails(salesDetails);
            // Update total value
            salesInvoice.setTotalValue(totalValue.get());
        }

        // Handle staff update
        if (updatedSalesInvoice.getStaff() != null) {
            Optional<Staff> staffOptional = staffRepository.findById(updatedSalesInvoice.getStaff().getId());
            salesInvoice.setStaff(staffOptional.orElse(salesInvoice.getStaff()));
        }

        // Handle customer update
        if (updatedSalesInvoice.getCustomer() != null) {
            Optional<Customer> customerOptional = customerRepository.findById(updatedSalesInvoice.getCustomer().getId());
            salesInvoice.setCustomer(customerOptional.orElse(salesInvoice.getCustomer()));
        }

        // Handle total value update - in case the user wants to change it manually
        salesInvoice.setTotalValue(Optional.ofNullable(updatedSalesInvoice.getTotalValue()).orElse(salesInvoice.getTotalValue()));

        // Handle date update
        salesInvoice.setDate(Optional.ofNullable(updatedSalesInvoice.getDate()).orElse(salesInvoice.getDate()));

        return salesInvoice;
    }
}

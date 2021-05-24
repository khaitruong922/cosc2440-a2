package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.filters.SalesInvoiceFilter;
import s3818074_s3818487.cosc2440a2.models.SalesInvoice;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.SalesDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.SalesInvoiceRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StaffService extends AbstractService<Staff, UUID> {
    private final SalesInvoiceRepository salesInvoiceRepository;

    @Autowired
    public StaffService(StaffRepository repo, SalesInvoiceRepository salesInvoiceRepository) {
        super(repo);
        this.salesInvoiceRepository = salesInvoiceRepository;
    }

    @Override
    public Staff updateById(Staff updatedStaff, UUID id) {
        Optional<Staff> staffOptional = repo.findById(id);
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");
        Staff staff = staffOptional.get();
        staff.setAddress(Optional.ofNullable(updatedStaff.getAddress()).orElse(staff.getAddress()));
        staff.setEmail(Optional.ofNullable(updatedStaff.getEmail()).orElse(staff.getEmail()));
        staff.setName(Optional.ofNullable(updatedStaff.getName()).orElse(staff.getName()));
        staff.setPhone(Optional.ofNullable(updatedStaff.getPhone()).orElse(staff.getPhone()));
        staff.setContactPerson(Optional.ofNullable(updatedStaff.getContactPerson()).orElse(staff.getContactPerson()));

        return staff;
    }

    public Double getRevenue(UUID id, Date startDate, Date endDate) {
        List<SalesInvoice> salesInvoices = new SalesInvoiceFilter(salesInvoiceRepository.findAll()).ofStaff(id).start(startDate).end(endDate).get();
        return salesInvoices.stream().mapToDouble(SalesInvoice::getTotalValue).sum();
    }

    public List<SalesInvoice> getSalesInvoices(UUID id, Date startDate, Date endDate, Integer page) {
        final int RESULTS_PER_PAGE = 5;
        return new SalesInvoiceFilter(salesInvoiceRepository.findAll(PageRequest.of(page, RESULTS_PER_PAGE)).toList()).ofStaff(id).start(startDate).end(endDate).get();
    }
}


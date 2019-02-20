package iot.database.repository;

import iot.database.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}

package iot.database.DTO;

import iot.database.controller.ProviderController;
import iot.database.domain.Invoice;
import iot.database.exceptions.NoSuchGoodException;
import iot.database.exceptions.NoSuchProviderException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.ResourceSupport;

import java.sql.Date;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class InvoiceDTO {
    Invoice invoice;

    public InvoiceDTO(Invoice invoice, Link selfLink) throws NoSuchGoodException, NoSuchProviderException {
        ResourceSupport rSupport = new ResourceSupport();
        this.invoice = invoice;
        rSupport.add(selfLink);
        rSupport.add(linkTo(ControllerLinkBuilder.methodOn(ProviderController.class).getProvidersByGoodID(invoice.getId())).withRel("invoices"));
    }

    public Long getInvoiceId() {
        return invoice.getId();
    }

    public Date getInvoiceDate() {return invoice.getDate();}
}

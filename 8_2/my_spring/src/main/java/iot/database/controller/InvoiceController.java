package iot.database.controller;

import iot.database.DTO.InvoiceDTO;
import iot.database.domain.Invoice;
import iot.database.exceptions.ExistsProviderForInvoiceException;
import iot.database.exceptions.NoSuchGoodException;
import iot.database.exceptions.NoSuchInvoiceException;
import iot.database.exceptions.NoSuchProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import iot.database.service.InvoiceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping(value = "/api/invoice")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() throws NoSuchProviderException, NoSuchGoodException, NoSuchInvoiceException {
        List<Invoice> invoiceList = invoiceService.getAllInvoice();
        Link link = linkTo(methodOn(InvoiceController.class).getAllInvoices()).withSelfRel();

        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        for (Invoice entity : invoiceList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            InvoiceDTO dto = new InvoiceDTO(entity, selfLink);
            invoicesDTO.add(dto);
        }

        return new ResponseEntity<>(invoicesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/provider/invoice/{invoice_id}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByProviderID(@PathVariable Long provider_id) throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        Set<Invoice> invoiceList = invoiceService.getInvoicesByProviderID(provider_id);

        Link link = linkTo(methodOn(ProviderController.class).getAllProviders()).withSelfRel();

        List<InvoiceDTO> invoiceDTO = new ArrayList<>();
        for (Invoice entity : invoiceList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            InvoiceDTO dto = new InvoiceDTO(entity, selfLink);
            invoiceDTO.add(dto);
        }

        return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/invoice/{invoice_id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long invoice_id) throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        Invoice invoice = invoiceService.getInvoice(invoice_id);
        Link link = linkTo(methodOn(InvoiceController.class).getInvoice(invoice_id)).withSelfRel();

        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice, link);

        return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/invoice/{invoice_id}")
    public  ResponseEntity<InvoiceDTO> addInvoice(@RequestBody Invoice newInvoice, @PathVariable Long provider_id) throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        invoiceService.createInvoice(newInvoice, provider_id);
        Link link = linkTo(methodOn(InvoiceController.class).getInvoice(newInvoice.getId())).withSelfRel();

        InvoiceDTO invoiceDTO = new InvoiceDTO(newInvoice, link);

        return new ResponseEntity<>(invoiceDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/invoice/{invoice_id}")
    public  ResponseEntity<InvoiceDTO> updateInvoice(@RequestBody Invoice uInvoice, @PathVariable Long invoice_id, @PathVariable Long provider_id) throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        invoiceService.updateInvoice(uInvoice, invoice_id, provider_id);
        Invoice invoice = invoiceService.getInvoice(invoice_id);
        Link link = linkTo(methodOn(InvoiceController.class).getInvoice(invoice_id)).withSelfRel();

        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice, link);

        return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/invoice/{invoice_id}")
    public  ResponseEntity deleteInvoice(@PathVariable Long invoice_id) throws NoSuchInvoiceException, ExistsProviderForInvoiceException {
        invoiceService.deleteInvoice(invoice_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

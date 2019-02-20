package iot.database.controller;

import iot.database.DTO.ProviderDTO;
import iot.database.domain.Provider;
import iot.database.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import iot.database.service.ProviderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ProviderController {
    @Autowired
    ProviderService providerService;

    @GetMapping(value = "/api/provider/{provider_id}")
    public ResponseEntity<ProviderDTO> getProvider(@PathVariable Long provider_id) throws NoSuchProviderException, NoSuchGoodException {
        Provider provider = providerService.getProvider(provider_id);
        Link link = linkTo(methodOn(ProviderController.class).getProvider(provider_id)).withSelfRel();

        ProviderDTO providerDTO = new ProviderDTO(provider, link);

        return new ResponseEntity<>(providerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/provider")
    public ResponseEntity<List<ProviderDTO>> getAllProviders() throws NoSuchProviderException, NoSuchGoodException {
        List<Provider> providerList = providerService.getAllProviders();
        Link link = linkTo(methodOn(ProviderController.class).getAllProviders()).withSelfRel();

        List<ProviderDTO> providersDTO = new ArrayList<>();
        for (Provider entity : providerList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            ProviderDTO dto = new ProviderDTO(entity, selfLink);
            providersDTO.add(dto);
        }

        return new ResponseEntity<>(providersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/provider/good/{good_id}")
    public ResponseEntity<List<ProviderDTO>> getProvidersByGoodID(@PathVariable Long good_id) throws NoSuchGoodException, NoSuchProviderException {
        Set<Provider> providerList = providerService.getProvidersByGoodId(good_id);
        Link link = linkTo(methodOn(ProviderController.class).getAllProviders()).withSelfRel();

        List<ProviderDTO> providersDTO = new ArrayList<>();
        for (Provider entity : providerList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            ProviderDTO dto = new ProviderDTO(entity, selfLink);
            providersDTO.add(dto);
        }

        return new ResponseEntity<>(providersDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/provider/invoice/{invoice_id}")
    public  ResponseEntity<ProviderDTO> addProvider(@RequestBody Provider newProvider, @PathVariable Long invoice_id)
            throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        providerService.createProvider(newProvider);
        Link link = linkTo(methodOn(ProviderController.class).getProvider(newProvider.getId())).withSelfRel();

        ProviderDTO providerDTO = new ProviderDTO(newProvider, link);

        return new ResponseEntity<>(providerDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/provider/{provider_id}/invoice/{invoice_id}")
    public  ResponseEntity<ProviderDTO> updateProvider(@RequestBody Provider uProvider,
                                                   @PathVariable Long provider_id, @PathVariable Long invoice_id)
            throws NoSuchInvoiceException, NoSuchProviderException, NoSuchGoodException {
        providerService.updateProvider(uProvider, provider_id);
        Provider provider = providerService.getProvider(provider_id);
        Link link = linkTo(methodOn(ProviderController.class).getProvider(provider_id)).withSelfRel();

        ProviderDTO providerDTO = new ProviderDTO(provider, link);

        return new ResponseEntity<>(providerDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/provider/{provider_id}")
    public  ResponseEntity deleteProvider(@PathVariable Long provider_id) throws NoSuchProviderException, ExistsGoodForProviderException {
        providerService.deleteProvider(provider_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/provider/{provider_id}/good/{good_id}")
    public  ResponseEntity<ProviderDTO> addGoodForProvider(@PathVariable Long provider_id, @PathVariable Long good_id)
            throws NoSuchProviderException, NoSuchGoodException, AlreadyExistsGoodInProviderException, GoodAbsentException {
        providerService.addGoodForProvider(provider_id,good_id);
        Provider provider = providerService.getProvider(provider_id);
        Link link = linkTo(methodOn(ProviderController.class).getProvider(provider_id)).withSelfRel();

        ProviderDTO providerDTO = new ProviderDTO(provider, link);

        return new ResponseEntity<>(providerDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/provider/{provider_id}/good/{good_id}")
    public  ResponseEntity<ProviderDTO> removeBookForProvider(@PathVariable Long provider_id, @PathVariable Long good_id)
            throws NoSuchProviderException, NoSuchGoodException, ProviderHasNoGoodException {
        providerService.removeGoodForProvider(provider_id,good_id);
        Provider provider = providerService.getProvider(provider_id);
        Link link = linkTo(methodOn(ProviderController.class).getProvider(provider_id)).withSelfRel();

        ProviderDTO providerDTO = new ProviderDTO(provider, link);

        return new ResponseEntity<>(providerDTO, HttpStatus.OK);
    }

}

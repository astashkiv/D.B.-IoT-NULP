package iot.database.DTO;

import iot.database.controller.GoodController;
import iot.database.domain.Provider;
import iot.database.exceptions.NoSuchGoodException;
import iot.database.exceptions.NoSuchProviderException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ProviderDTO {
    Provider provider;

    public ProviderDTO(Provider provider, Link selfLink) throws NoSuchGoodException, NoSuchProviderException {
        ResourceSupport rSupport = new ResourceSupport();
        this.provider = provider;
        rSupport.add(selfLink);
        rSupport.add(linkTo(ControllerLinkBuilder.methodOn(GoodController.class).getGoodsByProviderID(provider.getId())).withRel("goods"));
    }

    public Long getProviderId() {
        return provider.getId();
    }

    public String getProviderName() {return provider.getName();}

    public String getProviderDirector() {return provider.getDirector();}

    public String getProviderPhone() {return provider.getPhone();}
}

package iot.database.controller;

import iot.database.DTO.GoodDTO;
import iot.database.domain.Good;
import iot.database.exceptions.ExistsProviderForGoodException;
import iot.database.exceptions.NoSuchGoodException;
import iot.database.exceptions.NoSuchProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import iot.database.service.GoodService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GoodController {
    @Autowired
    GoodService goodService;

    @GetMapping(value = "/api/good/provider/{provider_id}")
    public ResponseEntity<List<GoodDTO>> getGoodsByProviderID(@PathVariable Long provider_id) throws NoSuchProviderException, NoSuchGoodException {
        Set<Good> goodList = goodService.getGoodsByProviderId(provider_id);
        Link link = linkTo(methodOn(GoodController.class).getAllGoods()).withSelfRel();

        List<GoodDTO> goodssDTO = new ArrayList<>();
        for (Good entity : goodList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            GoodDTO dto = new GoodDTO(entity, selfLink);
            goodssDTO.add(dto);
        }

        return new ResponseEntity<>(goodssDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/good/good_id}")
    public ResponseEntity<GoodDTO> getGood(@PathVariable Long good_id) throws NoSuchGoodException, NoSuchProviderException {
        Good good = goodService.getGood(good_id);
        Link link = linkTo(methodOn(GoodController.class).getGood(good_id)).withSelfRel();

        GoodDTO goodDTO = new GoodDTO(good, link);

        return new ResponseEntity<>(goodDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/good")
    public ResponseEntity<List<GoodDTO>> getAllGoods() throws NoSuchGoodException, NoSuchProviderException {
        List<Good> goodsList = goodService.getAllGoods();
        Link link = linkTo(methodOn(GoodController.class).getAllGoods()).withSelfRel();

        List<GoodDTO> goodDTO = new ArrayList<>();
        for (Good entity : goodsList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            GoodDTO dto = new GoodDTO(entity, selfLink);
            goodDTO.add(dto);
        }

        return new ResponseEntity<>(goodDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/good")
    public ResponseEntity<GoodDTO> addGood(@RequestBody Good newGood) throws NoSuchGoodException, NoSuchProviderException {
        goodService.createGood(newGood);
        Link link = linkTo(methodOn(GoodController.class).getGood(newGood.getId())).withSelfRel();

        GoodDTO goodDTO = new GoodDTO(newGood, link);

        return new ResponseEntity<>(goodDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/good/{good_id}")
    public ResponseEntity<GoodDTO> updateGood(@RequestBody Good uGood, @PathVariable Long good_id) throws NoSuchGoodException, NoSuchProviderException {
        goodService.updateGood(uGood, good_id);
        Good good = goodService.getGood(good_id);
        Link link = linkTo(methodOn(GoodController.class).getGood(good_id)).withSelfRel();

        GoodDTO goodDTO = new GoodDTO(good, link);

        return new ResponseEntity<>(goodDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/good/{good_id}")
    public  ResponseEntity deleteGood(@PathVariable Long good_id) throws ExistsProviderForGoodException, NoSuchGoodException {
        goodService.deleteGood(good_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

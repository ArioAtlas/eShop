package ir.rightel.bss.eshop.controllers;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CoreController {
    protected Link createHateoasLink(long id){
        return linkTo(getClass()).slash(id).withSelfRel();
    }
}

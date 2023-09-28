package com.attornatus.managepeople.dto;

import com.attornatus.managepeople.entities.Address;
import com.attornatus.managepeople.entities.People;

public class AddressDTO {

    private Long id;
    private String zipCode;
    private String street;
    private Integer number;
    private String city;
    private Boolean main;
    private People people;

    public AddressDTO() {
    }

    public AddressDTO(Address entity) {
        id = entity.getId();
        zipCode = entity.getZipCode();
        street = entity.getStreet();
        number = entity.getNumber();
        city = entity.getCity();
        main = entity.getMain();
        people = entity.getPeople();
    }

    public String getZipCode() {
        return zipCode;
    }

    public Long getId() { return id; }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public Boolean getMain() {
        return main;
    }

    public People getPeople() { return people; }
}

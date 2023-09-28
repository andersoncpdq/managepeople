package com.attornatus.managepeople.dto;

import com.attornatus.managepeople.entities.Address;
import com.attornatus.managepeople.entities.People;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeopleDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;

    public PeopleDTO() {
    }

    public PeopleDTO(People entity) {
        id = entity.getId();
        name = entity.getName();
        birthDate = entity.getBirthDate();
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}

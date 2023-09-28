package com.attornatus.managepeople;

import com.attornatus.managepeople.dto.AddressDTO;
import com.attornatus.managepeople.dto.PeopleDTO;
import com.attornatus.managepeople.entities.Address;
import com.attornatus.managepeople.entities.People;

import java.time.LocalDate;

public class Factory {

    public static People createPeople() {
        People people = new People(1L, "Carlos", LocalDate.parse("1989-10-05"));
        people.getAddresses().add(new Address(1L, "Rua tal", "60442056", 44, "Fortaleza", true, people));
        return people;
    }

    public static PeopleDTO createPeopleDTO() {
        People people = createPeople();
        return new PeopleDTO(people);
    }

    public static Address createAddress() {
        People people = new People(1L, "Carlos", LocalDate.parse("1989-10-05"));
        return new Address(1L, "Rua tal", "6044056", 44, "Fortaleza", true, people);
    }

    public static AddressDTO createAddressDTO() {
        Address address = createAddress();
        return new AddressDTO(address);
    }
}

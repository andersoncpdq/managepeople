package com.attornatus.managepeople.services;

import com.attornatus.managepeople.dto.AddressDTO;
import com.attornatus.managepeople.entities.Address;
import com.attornatus.managepeople.entities.People;
import com.attornatus.managepeople.repositories.AddressRepository;
import com.attornatus.managepeople.repositories.PeopleRepository;
import com.attornatus.managepeople.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Transactional(readOnly = true)
    public List<AddressDTO> findByPeopleId(Long peopleId) {
        if ( !peopleRepository.existsById(peopleId) )
            throw new ResourceNotFoundException("Recurso não encontrado");

        List<Address> addresses = addressRepository.searchByPeopleId(peopleId);
        return addresses.stream().map(AddressDTO::new).toList();
    }

    @Transactional
    public AddressDTO insert(AddressDTO dto) {
        People people = peopleRepository.findById(dto.getPeople().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        Address address = new Address();
        address.setPeople(people);
        address.setZipCode(dto.getZipCode());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());
        address.setMain(false);

        return new AddressDTO( addressRepository.save(address) );
    }

    @Transactional
    public void updateMainAddressByPeopleId(Long peopleId, AddressDTO dto) {
        if ( !peopleRepository.existsById(peopleId) )
            throw new ResourceNotFoundException("Recurso não encontrado");

        List<Address> addresses = addressRepository.searchByPeopleId(peopleId);
        addresses.stream().forEach(x -> x.setMain(false));

        for (Address address : addresses) {
            if (address.getId().equals(dto.getId()))
                address.setMain(dto.getMain());
        }
        addressRepository.saveAll(addresses);
    }
}

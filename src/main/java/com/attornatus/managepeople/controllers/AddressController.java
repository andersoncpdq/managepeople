package com.attornatus.managepeople.controllers;

import com.attornatus.managepeople.dto.AddressDTO;
import com.attornatus.managepeople.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/{peopleId}")
    public ResponseEntity<List<AddressDTO>> findByPeopleId(@PathVariable Long peopleId) {
        return ResponseEntity.ok( addressService.findByPeopleId(peopleId) );
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO dto) {
        dto = addressService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{peopleId}")
    public ResponseEntity<Void> updateMainAddressByPeopleId(@PathVariable Long peopleId, @RequestBody AddressDTO dto) {
        addressService.updateMainAddressByPeopleId(peopleId, dto);
        return ResponseEntity.noContent().build();
    }
}

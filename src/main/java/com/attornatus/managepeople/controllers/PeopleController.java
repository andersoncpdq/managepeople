package com.attornatus.managepeople.controllers;

import com.attornatus.managepeople.dto.PeopleDTO;
import com.attornatus.managepeople.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping
    public ResponseEntity<Page<PeopleDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok( peopleService.findAll(pageable) );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PeopleDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok( peopleService.findById(id) );
    }

    @PostMapping
    public ResponseEntity<PeopleDTO> insert(@RequestBody PeopleDTO dto) {
        dto = peopleService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PeopleDTO> update(@PathVariable Long id, @RequestBody PeopleDTO dto) {
        return ResponseEntity.ok( peopleService.update(id, dto) );
    }
}

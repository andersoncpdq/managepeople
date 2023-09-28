package com.attornatus.managepeople.services;

import com.attornatus.managepeople.dto.PeopleDTO;
import com.attornatus.managepeople.entities.People;
import com.attornatus.managepeople.repositories.PeopleRepository;
import com.attornatus.managepeople.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Transactional(readOnly = true)
    public Page<PeopleDTO> findAll(Pageable pageable) {
        Page<People> all = peopleRepository.findAll(pageable);
        return all.map(PeopleDTO::new);
    }

    @Transactional(readOnly = true)
    public PeopleDTO findById(Long id) {
        return new PeopleDTO( peopleRepository.findById(id)
                                .orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado") ) );
    }

    @Transactional
    public PeopleDTO insert(PeopleDTO dto) {
        People people = new People();
        copyDtoToEntity(dto, people);
        return new PeopleDTO( peopleRepository.save(people) );
    }

    @Transactional
    public PeopleDTO update(Long id, PeopleDTO dto) {
        try {
            People people = peopleRepository.getReferenceById(id);
            copyDtoToEntity(dto, people);
            return new PeopleDTO( peopleRepository.save(people) );
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    private void copyDtoToEntity(PeopleDTO dto, People entity) {
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
    }
}

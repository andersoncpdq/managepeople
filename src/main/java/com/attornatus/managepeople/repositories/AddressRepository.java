package com.attornatus.managepeople.repositories;

import com.attornatus.managepeople.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT obj FROM Address obj WHERE obj.people.id = :peopleId")
    List<Address> searchByPeopleId(Long peopleId);
}

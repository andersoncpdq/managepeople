package com.attornatus.managepeople.repositories;

import com.attornatus.managepeople.entities.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {
}

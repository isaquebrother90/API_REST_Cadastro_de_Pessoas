package com.isaque.peopleapi.repository;

import com.isaque.peopleapi.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}

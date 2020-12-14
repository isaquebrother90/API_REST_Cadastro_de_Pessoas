package com.isaque.peopleapi.controller;

import com.isaque.peopleapi.dto.request.PersonRequestDto;
import com.isaque.peopleapi.dto.response.PersonResponseDto;
import com.isaque.peopleapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequestMapping("api/v1/persons")
@Validated
public class PersonController {
    @Autowired
    PersonService service;

    @GetMapping
    public ResponseEntity<Page<PersonResponseDto>> index(Pageable page) {
        return ResponseEntity.ok(service.findAll(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> store(@RequestBody @Valid PersonRequestDto dto) {
        PersonResponseDto response = service.store(dto);
        return ResponseEntity
                .created(UtilController.generatedUri(response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonResponseDto> store(@PathVariable Long id, @RequestBody @Valid PersonRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        service.destroy(id);
        return ResponseEntity.noContent().build();
    }
}
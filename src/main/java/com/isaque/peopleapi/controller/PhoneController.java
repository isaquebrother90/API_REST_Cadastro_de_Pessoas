package com.isaque.peopleapi.controller;

import com.isaque.peopleapi.dto.request.PhoneRequestDto;
import com.isaque.peopleapi.dto.response.PhoneResponseDto;
import com.isaque.peopleapi.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("api/v1/phones")
@Validated
public class PhoneController {
    @Autowired
    PhoneService service;

    @GetMapping
    public ResponseEntity<Page<PhoneResponseDto>> index(Pageable page) {
        return ResponseEntity.ok(service.findAll(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<PhoneResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PhoneResponseDto> store(@RequestBody @Valid PhoneRequestDto dto) {
        PhoneResponseDto response = service.store(dto);
        return ResponseEntity
                .created(UtilController.generatedUri(response.getId()))
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<PhoneResponseDto> store(@PathVariable Long id, @RequestBody @Valid PhoneRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        service.destroy(id);
        return ResponseEntity.noContent().build();
    }
}
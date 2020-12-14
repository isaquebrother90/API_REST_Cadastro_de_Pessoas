package com.isaque.peopleapi.service;

import com.isaque.peopleapi.dto.request.PhoneRequestDto;
import com.isaque.peopleapi.dto.response.PhoneResponseDto;
import com.isaque.peopleapi.model.Person;
import com.isaque.peopleapi.model.Phone;
import com.isaque.peopleapi.repository.PhoneRepository;
import com.isaque.peopleapi.service.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    PhoneRepository repository;

    @Autowired
    PersonService personService;

    public Phone validatedById(Long id) {
        Optional<Phone> obj = repository.findById(id);
        obj.orElseThrow(() -> new BusinessException("phone", "phone was not found with id " + id, 404));
        return obj.get();
    }

    public Page<PhoneResponseDto> findAll(@NotNull Pageable page) {
        PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort());
        List<PhoneResponseDto> dto = repository
                .findAll(pageRequest)
                .stream()
                .map(PhoneResponseDto::fromResource)
                .collect(Collectors.toList());
        return new PageImpl<>(dto, pageRequest, page.getPageSize());
    }

    public PhoneResponseDto findById(@NotNull Long id) {
        return PhoneResponseDto.fromResource(this.validatedById(id));
    }

    public PhoneResponseDto store(@Valid PhoneRequestDto dto) {
        Person person = this.validatedPerson(dto.getPerson());
        Phone entity = dto.toResource(person);
        System.out.println(entity);
        return PhoneResponseDto.fromResource(repository.save(entity));
    }

    public PhoneResponseDto update(@NotNull Long id, @Valid PhoneRequestDto dto) {
        Phone obj = this.validatedById(id);
        Person person = this.validatedPerson(obj.getPerson().getId());
        BeanUtils.copyProperties(dto.toResource(person), obj, "id");

        return PhoneResponseDto.fromResource(repository.save(obj));
    }

    public void destroy(@NotNull Long id) {
        Phone obj = this.validatedById(id);
        repository.delete(obj);
    }

    private Person validatedPerson(Long id) {
        return personService.validatedById(id);
    }
}

package com.isaque.peopleapi.service;

import com.isaque.peopleapi.dto.request.PersonRequestDto;
import com.isaque.peopleapi.dto.response.PersonResponseDto;
import com.isaque.peopleapi.model.Person;
import com.isaque.peopleapi.repository.PersonRepository;
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
public class PersonService {

    @Autowired
    PersonRepository repository;

    public Person validatedById(Long id) {
        Optional<Person> obj = repository.findById(id);
        obj.orElseThrow(() -> new BusinessException("person", "person was not found with id " + id, 404));
        return obj.get();
    }

    public Page<PersonResponseDto> findAll(@NotNull Pageable page) {
        PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort());
        List<PersonResponseDto> dto = repository
                .findAll(pageRequest)
                .stream()
                .map(PersonResponseDto::fromResource)
                .collect(Collectors.toList());
        return new PageImpl<>(dto, pageRequest, page.getPageSize());
    }

    public PersonResponseDto findById(@NotNull Long id) {
        return PersonResponseDto.fromResource(this.validatedById(id));
    }

    public PersonResponseDto store(@Valid PersonRequestDto dto) {
        this.existsByCpf(dto.getCpf());
        return PersonResponseDto.fromResource(repository.save(dto.toResource()));
    }

    public PersonResponseDto update(@NotNull Long id, @Valid PersonRequestDto dto) {
        Person obj = this.validatedById(id);
        this.existsByCpf(dto.getCpf(), obj.getId());

        BeanUtils.copyProperties(dto.toResource(), obj, "id");

        return PersonResponseDto.fromResource(repository.save(obj));
    }

    public void destroy(@NotNull Long id) {
        Person obj = this.validatedById(id);
        repository.delete(obj);
    }

    private void existsByCpf(String email) {
        if (repository.existsByCpf(email)) throw new BusinessException("cpf", "cpf already exists", 400);
    }

    private void existsByCpf(String email, long id) {
        Optional<Person> obj = repository.findByCpf(email);
        if (obj.isPresent() && obj.get().getId() != id) throw new BusinessException("cpf", "cpf already exists", 400);
    }
}

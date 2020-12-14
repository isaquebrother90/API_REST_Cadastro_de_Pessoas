package com.isaque.peopleapi.dto.response;

import com.isaque.peopleapi.model.Person;
import com.isaque.peopleapi.model.Phone;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PersonResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    private List<Phone> phones;

    public static PersonResponseDto fromResource(Person person) {
        return new PersonResponseDto(person.getId(), person.getFirstName(), person.getLastName(), person.getCpf(), person.getBirthDate(), person.getPhones());
    }

}

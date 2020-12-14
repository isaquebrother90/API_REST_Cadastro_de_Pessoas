package com.isaque.peopleapi.dto.request;

import com.isaque.peopleapi.model.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class PersonRequestDto {

    @NotEmpty
    @Size(min = 5, max = 100)
    private String firstName;
    @NotEmpty
    @Size(min = 5, max = 100)
    private String lastName;
    @NotEmpty
    @CPF
    private String cpf;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    public Person toResource() {
        return new Person(null, this.firstName, this.lastName, this.cpf, this.birthDate, null);
    }
}

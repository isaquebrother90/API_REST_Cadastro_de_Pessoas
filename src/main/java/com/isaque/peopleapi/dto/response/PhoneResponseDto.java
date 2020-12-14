package com.isaque.peopleapi.dto.response;

import com.isaque.peopleapi.enums.PhoneTypeEnum;
import com.isaque.peopleapi.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PhoneResponseDto {

    private Long id;
    private Long person;
    private PhoneTypeEnum type;
    private Integer ddd;
    private Integer number;

    public static PhoneResponseDto fromResource(Phone phone) {
        return new PhoneResponseDto(phone.getId(),
                phone.getPerson().getId(),
                phone.getType(), phone.getDdd(), phone.getNumber());
    }
}

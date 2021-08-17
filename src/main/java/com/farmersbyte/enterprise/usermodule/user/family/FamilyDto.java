package com.farmersbyte.enterprise.usermodule.user.family;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDto extends BaseDto {
    private UUID userId;
    private Person spouse;
    private Person father;
    private Person mother;
    private List<Person> kids;
    private List<Person>  siblings;
}
